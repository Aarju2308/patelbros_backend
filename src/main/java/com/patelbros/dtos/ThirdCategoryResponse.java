package com.patelbros.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThirdCategoryResponse {
	private Integer id;
	
	private SubCategoryResponse subCategory;
	
	private String thirdCategory;
	
	private boolean active;
}
