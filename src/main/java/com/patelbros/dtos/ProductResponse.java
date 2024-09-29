package com.patelbros.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
	private Integer id;
	private String name;
	private String descrtiption;
	private double price;
	private boolean active;
	private BrandResponse brand;
	private ThirdCategoryResponse thirdCategory;
	private byte[] image;
	private double rating;
	
}
