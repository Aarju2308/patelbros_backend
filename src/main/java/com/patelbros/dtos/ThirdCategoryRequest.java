package com.patelbros.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ThirdCategoryRequest {
	@NotNull(message = "Category Is Required")
	@Min(value = 1, message = "Please Select Category")
	private Integer catId;
	
	@NotNull(message = "Sub Category Is Required")
	@Min(value = 1, message = "Please Select Sub Category")
	private Integer subCatId;
	
	@NotNull(message = "Third Category Is Required")
	@NotBlank(message = "Third Category Is Required")
	private String thirdCategroy;
	
	private boolean active;
}
