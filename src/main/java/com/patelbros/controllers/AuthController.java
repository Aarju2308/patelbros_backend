package com.patelbros.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patelbros.dtos.LoginRequest;
import com.patelbros.dtos.LoginResponse;
import com.patelbros.dtos.RegistrationRequest;
import com.patelbros.services.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) throws MessagingException {
		authenticationService.register(registrationRequest);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authenticationService.login(request));
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestParam String token) throws MessagingException {
		
		return ResponseEntity.ok(authenticationService.verifyUser(token));
	}
	
}
