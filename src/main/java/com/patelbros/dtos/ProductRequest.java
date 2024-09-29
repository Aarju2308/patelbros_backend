package com.patelbros.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
	
	@NotNull(message = "Category Is Required")
	@Min(value = 1, message = "Please Select Category")
	private Integer catId;
	
	@NotNull(message = "Sub Category Is Required")
	@Min(value = 1, message = "Please Select Sub Category")
	private Integer subCatId;
	
	@NotNull(message = "Product Name Is Required")
	@NotEmpty(message = "Product Name Is Required")
	private String name;
	
	@NotNull(message = "Product Description Is Required")
	@NotEmpty(message = "Product Description Is Required")
	private String description;

	@NotNull(message = "Product Price Is Required")
	private double price;
	
	private boolean active;
	
	@NotNull(message = "Third Category Is Required")
	private Integer thirdCategoryId;
	
	@NotNull(message = "Product Brand Is Required")
	private Integer brandId;
	
}
