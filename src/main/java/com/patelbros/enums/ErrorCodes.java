package com.patelbros.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCodes {

	NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No Code"),
	ACCOUNT_LOCKED(300,HttpStatus.FORBIDDEN,"Account Is Locked"),
	ACCOUNT_DISABLED(301,HttpStatus.FORBIDDEN,"Account Is Disabled"),
	BAD_CREDIANTIALS(302,HttpStatus.BAD_REQUEST,"Invalid Username Or Password"),	
	;
	
	private int code;
	private HttpStatus httpStatus;
	private String message;
	
	ErrorCodes(int code, HttpStatus status, String message){
		this.code = code;
		httpStatus = status;
		this.message = message;
	}
}
