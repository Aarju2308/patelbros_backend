package com.patelbros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.patelbros.dtos.PageResponse;
import com.patelbros.dtos.ThirdCategoryRequest;
import com.patelbros.dtos.ThirdCategoryResponse;
import com.patelbros.entities.ThirdCategory;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.mappers.ThirdCategoryMapper;
import com.patelbros.repositories.SubCategoryRepository;
import com.patelbros.repositories.ThirdCategoryRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThirdCategoryService {
	
	private final ThirdCategoryRepository thirdCategoryRepository;
	private final ThirdCategoryMapper thirdCategoryMapper;
	private final SubCategoryRepository subCategoryRepository;
	
	public Integer addThirdCategory(@Valid ThirdCategoryRequest request) {
		
		var subCat = subCategoryRepository.findById(request.getSubCatId()).orElseThrow(
				()->new OperationNotPermittedException("No Sub Category Found With Id : " +request.getSubCatId())
			);
		
		Optional<ThirdCategory> temp = thirdCategoryRepository.findBySubCategoryAndThirdCategory(subCat, request.getThirdCategroy());
		
		if (temp.isPresent()) {
			throw new OperationNotPermittedException("Third Category - " + request.getThirdCategroy() + " is already present in sub category - "+ subCat.getSubCategory());
		}
		
		ThirdCategory thirdCategory = thirdCategoryMapper.toThirdCategory(request, null);
		
		return thirdCategoryRepository.save(thirdCategory).getId();
	}

	public PageResponse<ThirdCategoryResponse> getAllThirdCategories(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		
		Page<ThirdCategory> thirdCategories = thirdCategoryRepository.findAll(pageable);
		
		List<ThirdCategoryResponse> thirdCatResponse = thirdCategories.stream()
				.map(t->thirdCategoryMapper.toThirdCatResoponse(t))
				.toList();
		
		
		return new PageResponse<ThirdCategoryResponse>(
					thirdCatResponse,
					thirdCategories.getNumber(),
					thirdCategories.getSize(),
					thirdCategories.getTotalElements(),
					thirdCategories.getTotalPages(),
					thirdCategories.isFirst(),
					thirdCategories.isLast()
				);
	}

	public ThirdCategoryResponse getSingleThirdCategory(Integer thirdCatId) {
		return thirdCategoryMapper.toThirdCatResoponse(
				
					thirdCategoryRepository.findById(thirdCatId).orElseThrow(
								()-> new OperationNotPermittedException("No third cat found with id : " + thirdCatId)
							)
				);
	}

	public List<ThirdCategoryResponse> getAllThirdCategoriesBySubCat(Integer subCatId) {

		var subCat = subCategoryRepository.findById(subCatId).orElseThrow(
					()->new OperationNotPermittedException("No Sub Category Found With Id : " +subCatId)
				);
		
		List<ThirdCategory> thirdCategories = thirdCategoryRepository.findBySubCategory(subCat);
		
		return thirdCategories.stream().map(t->thirdCategoryMapper.toThirdCatResoponse(t)).toList();
	}

	public Integer updateThirdCategory(Integer thirdCatId, @Valid ThirdCategoryRequest request) {
		
		var subCat = subCategoryRepository.findById(request.getSubCatId()).orElseThrow(
				()->new OperationNotPermittedException("No Sub Category Found With Id : " +request.getSubCatId())
			);
		
		Optional<ThirdCategory> temp = thirdCategoryRepository.findByThirdCategoryAndIdNot(request.getThirdCategroy(),thirdCatId);
		
		if (temp.isPresent()) {
			throw new OperationNotPermittedException("Third Category - " + request.getThirdCategroy() + " is already present in sub category - "+ subCat.getSubCategory());
		}
		
		return thirdCategoryRepository.save(thirdCategoryMapper.toThirdCategory(request, thirdCatId)).getId();
	}

	public void deleteThirdCategory(Integer thirdCatId) {
		thirdCategoryRepository.deleteById(thirdCatId);
	}

}
