package com.patelbros.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
	
	@NotBlank(message = "First Name Is Required")
	@NotEmpty(message = "First Name Is Required")
	private String firstName;
	
	@NotBlank(message = "Last Name Is Required")
	@NotEmpty(message = "Last Name Is Required")
	private String lastName;
	
	@NotBlank(message = "Email Is Required")
	@NotEmpty(message = "Email Is Required")
	@Email(message = "Invalid Email")
	private String email;
	
	@NotBlank(message = "Password Is Required")
	@NotEmpty(message = "Password Is Required")
	@Size(message = "Minimum 8 characters", min = 8)
	private String password;
}