package com.patelbros.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private Integer id;
	
	private String fullName;
	
	private String email;
	
	private String phone;
	
	private byte[] profile;
	
	private byte[] background;
	
	private boolean isLocked;
	
	private boolean isActive;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
