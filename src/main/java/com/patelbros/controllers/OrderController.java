package com.patelbros.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patelbros.dtos.OrderRequest;
import com.patelbros.dtos.OrderResponse;
import com.patelbros.dtos.PaypalResponse;
import com.patelbros.services.OrderService;
import com.patelbros.services.PaypalService;
import com.paypal.api.payments.Payment;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/user/order")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {
	
	private final OrderService orderService;
	private final PaypalService paypalService;
	
	@GetMapping("/")
	public ResponseEntity<List<OrderResponse>> getAllOrders(Authentication authentication) {
		return ResponseEntity.ok(orderService.getAllOrders(authentication));
	}
	
	@GetMapping("/{billNo}")
	public ResponseEntity<OrderResponse> getSingleOrder(@PathVariable String billNo,Authentication authentication) {
		return ResponseEntity.ok(orderService.getSingleOrder(billNo,authentication));
	}
	
	@PostMapping("/payForOrder")
	public ResponseEntity<PaypalResponse> payForOrder(@Valid @RequestBody OrderRequest request,Authentication authentication){
		return ResponseEntity.ok(orderService.payfororder(request,authentication));
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest request,Authentication authentication,String paymentId) throws MessagingException {
		orderService.createOrder(request,authentication,paymentId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/paymentSuccess")
	public ResponseEntity<String> paymentSuccess(
				@RequestParam String paymentId,
				@RequestParam String PayerId,
				Authentication authentication
			){
		try {
			Payment payment = paypalService.executePayment(paymentId, PayerId);
			if (payment.getState().equals("approved")) {
				orderService.createTempOrder(authentication, paymentId);
				return ResponseEntity.ok("approved");
			}
		} catch (Exception e) {
			return ResponseEntity.ok("failed");
		}
		
		return ResponseEntity.ok("failed");
	}

}
