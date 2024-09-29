package com.patelbros.mappers;

import org.springframework.stereotype.Service;

import com.patelbros.dtos.UserResponse;
import com.patelbros.entities.User;
import com.patelbros.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMapper {
	
	private final FileUtils fileUtils;
	
	public UserResponse toUserResponse(User user) {
		
		return UserResponse.builder()
				.id(user.getId())
				.fullName(user.getFirstName() + " " + user.getLastName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.background(fileUtils.readFile(user.getBackground()))
				.profile(fileUtils.readFile(user.getProfile()))
				.createdAt(user.getCreatedAt())
				.updatedAt(user.getUpdatedAt())
				.isActive(user.isEnabled())
				.isLocked(user.isLocked())
				.build();
	}
}
