package com.patelbros.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddressRequest {
	
	@NotBlank(message = "FirstName Is Needed")
	@NotEmpty(message = "FirstName Is Needed")
	private String firstName;
	
	@NotBlank(message = "Last Name Is Needed")
	@NotEmpty(message = "Last Name Is Needed")
	private String lastName;
	
	@NotBlank(message = "Address Is Needed")
	@NotEmpty(message = "Address Is Needed")
	private String address;
	
	@Min(value = 1, message = "City Is Needed")
	private Integer cityId;
}
