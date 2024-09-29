package com.patelbros.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CartRequest {
	
	@NotBlank(message = "Product is required")
	@NotEmpty(message = "Product is required")
	private Integer productId;
	
	@NotBlank(message = "Quantity is required")
	@NotEmpty(message = "Quantity is required")
	private int quantity;
	
	private int uniqueId;
}
