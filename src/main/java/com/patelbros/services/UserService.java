package com.patelbros.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.patelbros.dtos.PageResponse;
import com.patelbros.dtos.UserRequest;
import com.patelbros.dtos.UserResponse;
import com.patelbros.entities.User;
import com.patelbros.enums.EmailTemplates;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.mappers.UserMapper;
import com.patelbros.repositories.UserRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
//	private final PasswordEncoder encoder;
	private final FileStorageService fileStorageService;
	private final AuthenticationService authenticationService;

	public UserResponse getUserDetails(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		return userMapper.toUserResponse(user);
	}

	public void updateUserDetails(Authentication authentication, UserRequest request) throws MessagingException {
		User user = (User)authentication.getPrincipal();
		
		if (user.getPhone() != request.getPhone()) {
			var tempPhone = userRepository.findByPhoneAndIdNot(request.getPhone(), user.getId());
			if (tempPhone.isPresent()) {
				throw new OperationNotPermittedException("Another user already exists with the phone number : " + request.getPhone());
			}
			user.setPhone(request.getPhone());		
		}
		
		if (user.getEmail() != request.getEmail()) {
			var tempEmail = userRepository.findByEmailAndIdNot(request.getEmail(), user.getId());
			if (tempEmail.isPresent()) {
				throw new OperationNotPermittedException("Another user already exists with the email : " + request.getEmail());
			}
			
			user.setEnabled(false);
			user.setEmail(request.getEmail());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			authenticationService.sendConfirmationEmail(user, EmailTemplates.RE_CONFIRM_MAIL);
			userRepository.save(user);
		}
		
		user.setEmail(request.getEmail());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPhone(request.getPhone());	
		userRepository.save(user);
		
	}
	
	public void uploadProfiletImage(MultipartFile file, Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		String filePath = fileStorageService.saveFile(file,"user");
		user.setProfile(filePath);
		
		userRepository.save(user);
	}
	
	public void uploadBackgroundImage(MultipartFile file,Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		String filePath = fileStorageService.saveFile(file,"user");
		user.setBackground(filePath);
		userRepository.save(user);
		
	}

	public PageResponse<UserResponse> getUsersPage(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		
		Page<User> users = userRepository.findAll(pageable);
	
		List<UserResponse> responses = users.stream().map(
				u->userMapper.toUserResponse(u)
				).toList();
		
		return new PageResponse<UserResponse>(
				responses,
				users.getNumber(),
				users.getSize(),
				users.getTotalElements(),
				users.getTotalPages(),
				users.isFirst(),
				users.isLast()
				);
	}

	public void updateUserStatus(Integer userId, UserRequest request) {
		
		User user = userRepository.findById(userId).orElseThrow(
				 ()->new OperationNotPermittedException("could not find user with id : " + userId)
				);
		
		user.setLocked(request.isLocked());
		user.setEnabled(request.isActive());
		
		userRepository.save(user);
	}


}
