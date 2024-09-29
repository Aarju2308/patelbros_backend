package com.patelbros.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.patelbros.dtos.UserRequest;
import com.patelbros.dtos.UserResponse;
import com.patelbros.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name="User")
public class UserController {
	
	private final UserService userService;

	@GetMapping("/")
	public ResponseEntity<UserResponse> getUserDetails(Authentication authentication) {
		return ResponseEntity.ok(userService.getUserDetails(authentication));
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateUserDetails(Authentication authentication,@Valid @RequestBody UserRequest request) throws MessagingException {
		userService.updateUserDetails(authentication,request);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/profile", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadProfiletImage(Authentication authentication,@RequestPart("file") MultipartFile file) {
		userService.uploadProfiletImage(file, authentication);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/background", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadBackgroundImage(Authentication authentication,@RequestPart("file") MultipartFile file) {
		userService.uploadBackgroundImage(file, authentication);
		return ResponseEntity.ok().build();
	}
	
}
