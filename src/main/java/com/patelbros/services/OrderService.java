package com.patelbros.services;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.patelbros.dtos.OrderRequest;
import com.patelbros.dtos.OrderResponse;
import com.patelbros.dtos.PageResponse;
import com.patelbros.dtos.PaypalResponse;
import com.patelbros.entities.Address;
import com.patelbros.entities.Cart;
import com.patelbros.entities.City;
import com.patelbros.entities.Order;
import com.patelbros.entities.User;
import com.patelbros.enums.OrderStatus;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.mappers.OrderMapper;
import com.patelbros.repositories.OrderRepository;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
	
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
	
	private final OrderRepository orderRepository;
	private final AddressService addressService;
	private final CityService cityService;
	private final CartService cartService;
	private final PaypalService paypalService;
	private final OrderDetailService orderDetailService;
	private final EmailService emailService;
	private final OrderMapper orderMapper;
	
	private final String successUrl = "http://localhost:4200/success"; 
	private final String failedUrl = "http://localhost:4200/failed"; 
	
	public List<OrderResponse> getAllOrders(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		List<Order> orders = orderRepository.findByUser(user);
		return orders.stream().map(o->orderMapper.toOrderResponse(o)).toList();
	}

	public OrderResponse getSingleOrder(String billNo, Authentication authentication) {
		
		User user = (User)authentication.getPrincipal();
		
		Order order = orderRepository.findByBillNo(billNo).orElseThrow(
				()->new OperationNotPermittedException("No order found with id : "+billNo)
			);
	
		
		if (order.getUser().getId().compareTo(user.getId()) != 0) {
			throw new OperationNotPermittedException("You have no access to view order : "+billNo);
		}
		
		return orderMapper.toOrderResponse(order);
	}

	public void createOrder(@Valid OrderRequest request, Authentication authentication, String paymentId) throws MessagingException {
		
		User user = (User)authentication.getPrincipal();
		Address address = addressService.getSingleAddress(request.getAddressId());
		String billNo = generateBillNumber(12);
		int totalProducts = cartService.getProductsCount(user);
		double netAmount = cartService.getCartNetAmount(user) + address.getCity().getEstimatedShippingCost();
		OrderStatus orderStatus = OrderStatus.PENDING;
		String payment = "Paypal";
		
		Order order = orderRepository.findByPaymentId(paymentId).orElseThrow(
					()->new OperationNotPermittedException("No order found with Payment Id : "+paymentId)
				);
		
		order.setUser(user);
		order.setAddress(address);
		order.setBillNo(billNo);
		order.setTotalProducts(totalProducts);
		order.setNetAmount(netAmount);
		order.setStatus(orderStatus);
		order.setPayment(payment);
		
		Integer orderId = orderRepository.save(order).getId();
		
		List<Cart> carts =  orderDetailService.addProducts(orderId,authentication);
		
		emailService.sendOrderConfirmationMail(user.getEmail(), user.getName(), billNo, netAmount, carts);
		
	}
	
	
	private static String generateBillNumber(int length) {
        StringBuilder billNumber = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            billNumber.append(CHARACTERS.charAt(index));
        }

        return billNumber.toString();
    }
	
	
	public PaypalResponse payfororder(OrderRequest request,Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		City city = cityService.findById(request.getAddressId()).orElseThrow(
				()->new OperationNotPermittedException("No city found with id : " + request.getAddressId())
				);

		double netAmount = cartService.getCartNetAmount(user) + city.getEstimatedShippingCost();
		
		PaypalResponse response = PaypalResponse.builder()
				.total(netAmount)
				.currency("USD")
				.build();
		
		try {
			Payment payment = paypalService.createPayment(
					netAmount,
					"USD", 
					"paypal", 
					"sale", 
					"Payment Description", 
					failedUrl, 
					successUrl
			);
			
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					response.setUrl(link.getHref());
					return response;
					
				}
			}
		} catch (Exception e) {
			log.error("Error occured : " + e.getMessage());
		}
		response.setUrl(failedUrl);
		return response;
	}

	public void createTempOrder(Authentication authentication, String paymentId) {
		Order order = Order.builder()
				.paymentId(paymentId)
				.build();
		
		orderRepository.save(order);
	}

	public PageResponse<OrderResponse> getOrderPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		
		Page<Order> orders = orderRepository.findAll(pageable);
		
		List<OrderResponse> orderResponses = orders.stream().map(
				o->orderMapper.toOrderResponse(o)).toList();
		
		
		return new PageResponse<OrderResponse>(
				orderResponses,
				orders.getNumber(),
				orders.getSize(),
				orders.getTotalElements(),
				orders.getTotalPages(),
				orders.isFirst(),
				orders.isLast()
				
				);
	}

	public void updateOrderStatus(Integer orderId, OrderRequest orderRequest) {
		
		OrderStatus status = orderRequest.getOrderStatus();
		if (status == null) {
            throw new  OperationNotPermittedException("Invalid order status");
        }
		
		Order order = orderRepository.findById(orderId).orElseThrow(
				()->new OperationNotPermittedException("No order found with Order Id : "+orderId)
			);
		
		order.setStatus(orderRequest.getOrderStatus());
		orderRepository.save(order);
	}
	
	
}
