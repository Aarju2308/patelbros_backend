package com.patelbros.exceptions;

@SuppressWarnings("serial")
public class OperationNotPermittedException extends RuntimeException {
	public OperationNotPermittedException(String message){
		super(message);
	}
}
