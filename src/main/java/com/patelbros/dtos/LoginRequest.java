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
public class LoginRequest {
	@NotBlank(message = "Email Is Required")
	@NotEmpty(message = "Email Is Required")
	@Email(message = "Invalid Email")
	private String email;
	
	@NotBlank(message = "Password Is Required")
	@NotEmpty(message = "Password Is Required")
	@Size(message = "Minimum 8 characters", min = 8)
	private String password;
}
