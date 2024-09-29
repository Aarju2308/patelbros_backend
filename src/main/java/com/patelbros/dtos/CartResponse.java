package com.patelbros.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CartResponse {
	
	private Integer id;
	
	private ProductResponse product;
	
	private int quantity;
	
	private double total;
	
}
