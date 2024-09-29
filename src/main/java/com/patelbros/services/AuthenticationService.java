package com.patelbros.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.patelbros.dtos.LoginRequest;
import com.patelbros.dtos.LoginResponse;
import com.patelbros.dtos.RegistrationRequest;
import com.patelbros.entities.Token;
import com.patelbros.entities.User;
import com.patelbros.enums.EmailTemplates;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.RoleRepository;
import com.patelbros.repositories.TokenRepository;
import com.patelbros.repositories.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final TokenRepository tokenRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	
	
	public void register(@Valid RegistrationRequest registrationRequest) throws MessagingException {
		var userRole = roleRepository.findByRole("ROLE_USER").orElseThrow(
				()-> new OperationNotPermittedException("No role found to assign"));
		
		User user = User.builder()
				.firstName(registrationRequest.getFirstName())
				.lastName(registrationRequest.getLastName())
				.email(registrationRequest.getEmail())
				.password(passwordEncoder.encode(registrationRequest.getPassword()))
				.roles(List.of(userRole))
				.enabled(false)
				.locked(false)
				.build();
		
		userRepository.save(user);
		sendConfirmationEmail(user,EmailTemplates.ACTIVATION_MAIL);
		
	}

	public void sendConfirmationEmail(User user, EmailTemplates emailTemplate) throws MessagingException {
		
		String randomToken = String.format("%06d", new Random().nextInt(999999));
		
		var token = Token.builder()
				.user(user)
				.token(randomToken)
				.createdAt(LocalDateTime.now())
				.expiresAt(LocalDateTime.now().plusMinutes(15))
				.build();
		
		tokenRepository.save(token);
		
		emailService.sendMail(user.getEmail(),user.getName(),emailTemplate,randomToken,"Activation Code");
		
	}


	public LoginResponse login(@Valid LoginRequest request) {
		
		var test = userRepository.findByEmail(request.getEmail());
		if(test.isEmpty()) {
			throw new OperationNotPermittedException("No user exists with email : "+ request.getEmail());
		}
		
		var auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
				);
		var claims = new HashMap<String, Object>();
		User user = (User)auth.getPrincipal();
		
		claims.put("fullname", user.getName());
		var jwtToken = jwtService.createToken(claims, user);
		return LoginResponse.builder().jwt(jwtToken).build();
	}


	public String verifyUser(String token) throws MessagingException {
		var tempToken = tokenRepository.findByToken(token).orElseThrow(()-> new OperationNotPermittedException("Invalid Token"));
		
		
		if (tempToken.getExpiresAt().isBefore(LocalDateTime.now())) {
			sendConfirmationEmail(tempToken.getUser(),EmailTemplates.ACTIVATION_MAIL);
			throw new OperationNotPermittedException("Token has been expired. Check mail for new token");
		}
		
		if (tempToken.getUser().isEnabled()) {
			throw new OperationNotPermittedException("User is already enabled");
		}
		
		User user = tempToken.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		tempToken.setVerifiedAt(LocalDateTime.now());
		tokenRepository.save(tempToken);
		return "Account activated successfully";
	}

	
}
