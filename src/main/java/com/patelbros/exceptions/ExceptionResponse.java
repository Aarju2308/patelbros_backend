package com.patelbros.exceptions;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExceptionResponse {
	

	private Integer errorCode;
	
	private String errorMessage;
	
	private String description;
	
	private Set<String> validationErrors;
	
	private Map<String, String> errors;
}
