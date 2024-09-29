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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	
	
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
	
	@Size(message = "Minimum 8 characters", min = 8)
	private String password;
	
	@Size(message = "Should have only 10 digits", min = 10, max = 10)
	private String phone;
	
	private boolean isActive;
	
	private boolean isLocked;
	
}
