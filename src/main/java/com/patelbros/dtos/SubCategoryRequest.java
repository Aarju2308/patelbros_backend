package com.patelbros.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubCategoryRequest {
	
	@NotNull(message = "Category Is Required")
	private Integer catId;
	
	@NotBlank(message = "Sub-Category Is Required")
	@NotEmpty(message = "Sub-Category Is Required")
	private String subCategory;
	
	private boolean active;
}
