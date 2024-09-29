package com.patelbros.exceptions;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.patelbros.enums.ErrorCodes;

import jakarta.mail.MessagingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<ExceptionResponse> handlerException(LockedException exception){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					ExceptionResponse.builder()
					.errorCode(ErrorCodes.ACCOUNT_LOCKED.getCode())
					.errorMessage(exception.getMessage())
					.description(ErrorCodes.ACCOUNT_LOCKED.getMessage())
					.build()
				);
	}
	
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ExceptionResponse> handlerException(DisabledException exception){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					ExceptionResponse.builder()
					.errorCode(ErrorCodes.ACCOUNT_DISABLED.getCode())
					.errorMessage(exception.getMessage())
					.description(ErrorCodes.ACCOUNT_DISABLED.getMessage())
					.build()
				);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> handlerException(BadCredentialsException exception){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					ExceptionResponse.builder()
					.errorCode(ErrorCodes.BAD_CREDIANTIALS.getCode())
					.errorMessage(exception.getMessage())
					.description(ErrorCodes.BAD_CREDIANTIALS.getMessage())
					.build()
				);
	}
	
	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<ExceptionResponse> handlerException(MessagingException exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					ExceptionResponse.builder()
					.errorMessage(exception.getMessage())
					.build()
				);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handlerException(MethodArgumentNotValidException exception){
		Set<String> errors = new HashSet<String>();
		exception.getBindingResult().getAllErrors()
			.forEach(err -> {
				var error = err.getDefaultMessage();
				errors.add(error);
			});
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					ExceptionResponse.builder()
					.validationErrors(errors)
					.build()
				);
	}
	
	@ExceptionHandler(OperationNotPermittedException.class)
	public ResponseEntity<ExceptionResponse> handlerException(OperationNotPermittedException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					ExceptionResponse.builder()
					.errorMessage(exception.getMessage())
					.build()
				);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handlerException(Exception exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					ExceptionResponse.builder()
					.errorCode(ErrorCodes.NO_CODE.getCode())
					.errorMessage(exception.getMessage())
					.description(ErrorCodes.NO_CODE.getMessage())
					.build()
				);
	}
	
}