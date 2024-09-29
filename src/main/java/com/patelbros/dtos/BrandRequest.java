package com.patelbros.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BrandRequest {
	@NotBlank(message = "Brand is Required")
	@NotEmpty(message = "Brand is Required")
	private String brand;
	
	@NotNull(message = "Status is Required")
	private boolean active;
}
