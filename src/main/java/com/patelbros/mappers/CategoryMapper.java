package com.patelbros.mappers;

import org.springframework.stereotype.Service;

import com.patelbros.dtos.CategoryResponse;
import com.patelbros.entities.Category;

@Service
public class CategoryMapper {
	
	public CategoryResponse toCategoryResponse(Category category) {
		return CategoryResponse.builder()
				.id(category.getId())
				.category(category.getCategory())
				.active(category.isActive())
				.build();
	}
	
	public Category toCategory(CategoryResponse response) {
		return Category.builder()
				.id(response.getId())
				.category(response.getCategory())
				.active(response.isActive())
				.build();
	}
}
