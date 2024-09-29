package com.patelbros.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandResponse {
	private Integer id;
	private String brand;
	private byte[] logo;
	private boolean active;
}
