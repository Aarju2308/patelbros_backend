package com.patelbros.mappers;

import org.springframework.stereotype.Service;

import com.patelbros.dtos.OrderDetailResponse;
import com.patelbros.dtos.OrderResponse;
import com.patelbros.entities.Order;
import com.patelbros.entities.OrderDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderMapper {
	
	private final ProductMapper productMapper;
	
	public OrderResponse toOrderResponse(Order order) {
		return OrderResponse.builder()
				.id(order.getId())
				.address(order.getAddress())
				.billNo(order.getBillNo())
				.netAmount(order.getNetAmount())
				.totalProducts(order.getTotalProducts())
				.status(order.getStatus())
				.payment(order.getPayment())
				.orderDetails(order.getOrderDetails().stream().map(o->toOrderDetailResponse(o)).toList())
				.issuedAt(order.getCreatedAt())
				.build();
	}
	
	public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
		return OrderDetailResponse.builder()
				.id(orderDetail.getId())
				.product(productMapper.toProductResponse(orderDetail.getProduct()))
				.quantity(orderDetail.getQuantity())
				.total(orderDetail.getTotal())
				.build();
	}
	
}
