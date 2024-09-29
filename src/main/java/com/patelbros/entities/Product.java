package com.patelbros.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity {
	
	private String name;
	
	@Column(length = 2000)
	private String description;

	private double price;
	
	private String picture;
	
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "thirdCatId")
	private ThirdCategory thirdCategory;

	@ManyToOne
	@JoinColumn(name = "brandId")
	private Brand brand;

	@OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
	private List<Feedback> feedbacks;
	
	@OneToMany(mappedBy = "product")
	private List<Cart> carts;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;
	
	@Transient
	public double getRating() {
		if (feedbacks.isEmpty() || feedbacks == null) {
			return 0.0;
		}
		var rating = feedbacks.stream()
				.mapToDouble(Feedback :: getRating)
				.average()
				.orElse(0.0);
		
		return Math.round(rating);
	}
}