package com.patelbros.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.patelbros.entities.Cart;
import com.patelbros.entities.Order;
import com.patelbros.entities.OrderDetail;
import com.patelbros.entities.User;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.CartRepository;
import com.patelbros.repositories.OrderDetailRepository;
import com.patelbros.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
	
	private final CartRepository cartRepository;
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	
	
	public List<Cart> addProducts(Integer orderId, Authentication authentication) {
		
		User user = (User)authentication.getPrincipal();
		
		Order order = orderRepository.findById(orderId).orElseThrow(
				()->new OperationNotPermittedException("No order found with id : "+orderId)
			);
		
		List<Cart> carts = cartRepository.findByUser(user);
		
		for (Cart cart : carts) {
			OrderDetail detail = OrderDetail.builder()
					.order(order)
					.product(cart.getProduct())
					.quantity(cart.getQuantity())
					.total(cart.getTotal())
					.build();
			
			orderDetailRepository.save(detail);
			cartRepository.delete(cart);
		}
		
		return carts;
		
	}

}
