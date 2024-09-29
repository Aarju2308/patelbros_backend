package com.patelbros.mappers;

import org.springframework.stereotype.Service;

import com.patelbros.dtos.ThirdCategoryRequest;
import com.patelbros.dtos.ThirdCategoryResponse;
import com.patelbros.entities.SubCategory;
import com.patelbros.entities.ThirdCategory;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.SubCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThirdCategoryMapper {
	private final SubCategoryRepository subCategoryRepository;
	private final SubCategoryMapper subCategoryMapper;
	
	public ThirdCategory toThirdCategory(ThirdCategoryRequest request,Integer thirdCatId) {
		
		SubCategory subCategory = subCategoryRepository.findById(request.getSubCatId()).orElseThrow(
					()-> new OperationNotPermittedException("No Sub Category Found With Id : " + request.getSubCatId())
				);
		
		return ThirdCategory.builder()
				.id(thirdCatId)
				.subCategory(subCategory)
				.thirdCategory(request.getThirdCategroy())
				.active(request.isActive())
				.build();
	}
	
	public ThirdCategoryResponse toThirdCatResoponse(ThirdCategory thirdCategory) {
			return ThirdCategoryResponse.builder()
					.id(thirdCategory.getId())
					.subCategory(subCategoryMapper.toSubCategoryResponse(thirdCategory.getSubCategory()))
					.thirdCategory(thirdCategory.getThirdCategory())
					.active(thirdCategory.isActive())
					.build();
	}
	
	public ThirdCategory fromResponse(ThirdCategoryResponse response) {
		return ThirdCategory.builder()
				.subCategory(subCategoryMapper.toSubCategory(response.getSubCategory()))
				.thirdCategory(response.getThirdCategory())
				.id(response.getId())
				.active(response.isActive())
				.build();
	}
}
