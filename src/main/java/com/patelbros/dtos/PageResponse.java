package com.patelbros.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
	
	private List<T> content;
	
	private int number;
	
	private int size;
	
	private long totalElements;
	
	private int totalPages;
	
	private boolean isFirst;
	
	private boolean isLast;
	
}