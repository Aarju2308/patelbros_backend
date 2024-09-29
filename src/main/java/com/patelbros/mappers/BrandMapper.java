package com.patelbros.mappers;

import org.springframework.stereotype.Service;

import com.patelbros.dtos.BrandResponse;
import com.patelbros.entities.Brand;
import com.patelbros.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandMapper {
	
	private final FileUtils fileUtils;
	
	public BrandResponse toBrandResponse(Brand b) {
		return BrandResponse.builder()
				.id(b.getId())
				.brand(b.getBrand())
				.logo(fileUtils.readFile(b.getLogo()))
				.active(b.isActive())
				.build();
	}
}
