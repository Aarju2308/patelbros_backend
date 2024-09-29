package com.patelbros.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.patelbros.entities.Address;
import com.patelbros.enums.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
	
	private Integer id;
	
	private String billNo;
	
	private int totalProducts;
	
	private double netAmount;
	
	@Enumerated(EnumType.STRING)
    private OrderStatus status; 
	
	private String payment;

	private Address address;
	
	private List<OrderDetailResponse> orderDetails;
	
	private LocalDateTime issuedAt;
}
