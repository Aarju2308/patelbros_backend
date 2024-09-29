package com.patelbros.dtos;

import com.patelbros.enums.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class OrderRequest {
	
	@Min(value = 1, message = "Invalid Address")
	private Integer addressId;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
}
