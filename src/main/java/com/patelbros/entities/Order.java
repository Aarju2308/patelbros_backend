package com.patelbros.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.patelbros.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "orders")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
		
	private String billNo;
	
	private String paymentId;
	
	private int totalProducts;
	
	private double netAmount;
	
	@Enumerated(EnumType.STRING)
    private OrderStatus status; 
	
	private String payment;
	
	@ManyToOne
	@JoinColumn(name = "addressId")
	private Address address;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;
	
}
