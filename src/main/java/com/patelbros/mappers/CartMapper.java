package com.patelbros.mappers;

import org.springframework.stereotype.Service;

import com.patelbros.dtos.CartResponse;
import com.patelbros.entities.Cart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartMapper {
	
	private final ProductMapper productMapper;
	
	public CartResponse toResponse(Cart cart) {
		return CartResponse.builder()
				.id(cart.getId())
				.product(productMapper.toProductResponse(cart.getProduct()))
				.quantity(cart.getQuantity())
				.total(cart.getTotal())
				.build();
	}
}
