package com.patelbros.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCategoryResponse {
	private Integer id;
	
	private CategoryResponse category;
	
	private String subCategory;
	
	private boolean active;
}
