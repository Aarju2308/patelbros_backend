package com.patelbros.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
	
	private Integer id;
	
	private ProductResponse product;
	
	private int quantity;
	
	private double total;
	
}
