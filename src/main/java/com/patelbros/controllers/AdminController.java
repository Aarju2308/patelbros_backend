package com.patelbros.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patelbros.dtos.OrderRequest;
import com.patelbros.dtos.OrderResponse;
import com.patelbros.dtos.PageResponse;
import com.patelbros.dtos.UserRequest;
import com.patelbros.dtos.UserResponse;
import com.patelbros.services.OrderService;
import com.patelbros.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin")
public class AdminController {
	private final OrderService orderService;
	private final UserService userService;
	
	@GetMapping("/users/")
	public ResponseEntity<PageResponse<UserResponse>> getUsersPage(
			@RequestParam(name = "page", required = false, defaultValue = "0")int page,
			@RequestParam(name = "size", required = false, defaultValue = "10")int size
			) {
		return ResponseEntity.ok(userService.getUsersPage(page,size));
	}
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<?> updateUserStatus(@PathVariable Integer userId, @RequestBody UserRequest request) {
		userService.updateUserStatus(userId,request);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/orders")
	public ResponseEntity<PageResponse<OrderResponse>> getOrdersPage(
			@RequestParam(name = "page", required = false, defaultValue = "0")int page,
			@RequestParam(name = "size", required = false, defaultValue = "10")int size
			) {
		return ResponseEntity.ok(orderService.getOrderPage(page,size));
	}
	
	@PutMapping("/order/{orderId}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable Integer orderId, @RequestBody OrderRequest orderRequest) {
		orderService.updateOrderStatus(orderId,orderRequest);
		return ResponseEntity.ok().build();
	}
		
}