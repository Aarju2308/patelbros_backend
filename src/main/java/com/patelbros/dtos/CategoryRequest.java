package com.patelbros.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
	@NotEmpty(message = "Category Is Required")
	@NotNull(message = "Category Is Required")
	private String category;
	
	private boolean active;
}
