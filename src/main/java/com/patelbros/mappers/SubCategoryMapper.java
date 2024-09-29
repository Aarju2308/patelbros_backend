package com.patelbros.mappers;

import org.springframework.stereotype.Service;

import com.patelbros.dtos.SubCategoryRequest;
import com.patelbros.dtos.SubCategoryResponse;
import com.patelbros.entities.Category;
import com.patelbros.entities.SubCategory;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubCategoryMapper {
	
	private final CategoryMapper categoryMapper;
	private final CategoryRepository categoryRepository;
	
	public SubCategoryResponse toSubCategoryResponse(SubCategory subCategory) {
		return SubCategoryResponse.builder()
		.category(categoryMapper.toCategoryResponse(subCategory.getCategory()))
		.id(subCategory.getId())
		.subCategory(subCategory.getSubCategory())
		.active(subCategory.isActive())
		.build();
	}
	
	public SubCategory toSubCategory(SubCategoryResponse response) {
		return SubCategory.builder()
				.id(null)
				.category(categoryMapper.toCategory(response.getCategory()))
				.subCategory(response.getSubCategory())
				.active(response.isActive())
				.build();
	}
	
	public SubCategory fromSubCatRequest(SubCategoryRequest request,Integer subCatId) {
		
		Category category = categoryRepository.findById(request.getCatId()).orElseThrow(
				() -> new OperationNotPermittedException("No Category With Id Found :" + request.getCatId())
				);
		
		return SubCategory.builder()
				.id(subCatId)
				.category(category)
				.subCategory(request.getSubCategory())
				.active(request.isActive())
				.build();
	}
}
