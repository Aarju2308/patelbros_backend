package com.patelbros.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patelbros.dtos.CartRequest;
import com.patelbros.dtos.CartResponse;
import com.patelbros.services.CartService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User")
public class CartController {
	private final CartService cartService;
	
	@PostMapping("/cart")
	public ResponseEntity<Integer> addToCart(
			@RequestBody CartRequest request,
			Authentication authentication
	) {
		if(authentication != null) {
			System.out.println(authentication.getName());
		}
		
		return ResponseEntity.ok(cartService.addToCart(request,authentication));
	}
	
	@PutMapping("cart/{uniqueId}")
	public ResponseEntity<?> setUserToCart(@PathVariable int uniqueId, Authentication authentication) {
		cartService.updateUser(uniqueId,authentication);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/countCart")
	public ResponseEntity<Long> getCartCountByUser(Authentication authentication) {
		return ResponseEntity.ok(cartService.getCartCountByUser(authentication));
	}
	
	@GetMapping("/cart")
	public ResponseEntity<List<CartResponse>> getCartByUser(
			@RequestParam(name = "page", required = false, defaultValue = "0")int page,
			@RequestParam(name = "size", required = false, defaultValue = "10")int size,
			Authentication authentication
	) {
		return ResponseEntity.ok(cartService.getCartByUser(authentication));
	}
	
	@PostMapping("/removeFromCart")
	public ResponseEntity<Integer> removeQuantityFromCart(@RequestBody CartRequest request,Authentication authentication) {
		return ResponseEntity.ok(cartService.removeFromCart(request, authentication));
	}
	
	@DeleteMapping("/cart/{cartId}")
	public ResponseEntity<?> deleteCart(@PathVariable Integer cartId,Authentication authentication) {
		cartService.deleteCart(cartId);
		return ResponseEntity.ok().build();
	}
	
	
}
