package com.patelbros.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.patelbros.dtos.PageResponse;
import com.patelbros.dtos.SubCategoryRequest;
import com.patelbros.dtos.SubCategoryResponse;
import com.patelbros.entities.SubCategory;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.mappers.SubCategoryMapper;
import com.patelbros.repositories.CategoryRepository;
import com.patelbros.repositories.SubCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
	
	private final CategoryRepository categoryRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final SubCategoryMapper subCategoryMapper;
	

	public Integer save(SubCategoryRequest request) {
		var cat = categoryRepository.findById(request.getCatId()).orElseThrow(
					() -> new OperationNotPermittedException("No Category With Id Found :" + request.getCatId())
				);
		var subCat = subCategoryRepository.findByCategoryAndSubCategory(cat,request.getSubCategory());
		
		if (subCat.isPresent()) {
			throw new OperationNotPermittedException("SubCategory " + request.getSubCategory() + " Already Exists In " + cat.getCategory());
		}
		
		SubCategory subCategory = SubCategory.builder()
				.id(null)
				.category(cat)
				.subCategory(request.getSubCategory())
				.active(request.isActive())
				.build();
		return subCategoryRepository.save(subCategory).getId();
	}

	public PageResponse<SubCategoryResponse> getSubCategyPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		
		Page<SubCategory> subCategories = subCategoryRepository.findAll(pageable);
		
		List<SubCategoryResponse> subCategoryResponses = subCategories.stream()
				.map(s -> subCategoryMapper.toSubCategoryResponse(s)).toList();
		
		return new PageResponse<SubCategoryResponse>(
				subCategoryResponses,
				subCategories.getNumber(),
				subCategories.getSize(),
				subCategories.getTotalElements(),
				subCategories.getTotalPages(),
				subCategories.isFirst(),
				subCategories.isLast()
		);
	}

	public SubCategoryResponse getSingleSubCategory(Integer subCatId) {
		SubCategory subCategory = subCategoryRepository.findById(subCatId).orElseThrow(
					() -> new OperationNotPermittedException("No SubCategory Present With Id : "+subCatId)
				);
		
		return subCategoryMapper.toSubCategoryResponse(subCategory);
				
	}

	public List<SubCategoryResponse> getAllSubCategoriesByCategory(Integer catId) {
		var cat = categoryRepository.findById(catId).orElseThrow(
				() -> new OperationNotPermittedException("No Category With Id Found :" + catId)
			);
		
		
		List<SubCategory> subCategories = subCategoryRepository.findByCategory(cat);
		
		return subCategories.stream()
				.map(s -> subCategoryMapper.toSubCategoryResponse(s))
				.toList();
	}
	
	public List<SubCategoryResponse> getAllSubCategories() {
				
		List<SubCategory> subCategories = subCategoryRepository.findAll();
		
		return subCategories.stream()
				.map(s -> subCategoryMapper.toSubCategoryResponse(s))
				.toList();
	}

	public Integer update(SubCategoryRequest request, Integer subCatId) {
		
		var cat = categoryRepository.findById(request.getCatId()).orElseThrow(
				() -> new OperationNotPermittedException("No Category With Id Found :" + request.getCatId())
			);
		var subCat = subCategoryRepository.findBySubCategoryAndIdNot(request.getSubCategory(),subCatId);
	
		if (subCat.isPresent()) {
			throw new OperationNotPermittedException("SubCategory " + request.getSubCategory() + " Already Exists In " + cat.getCategory());
		}
		
		SubCategory subCategory = subCategoryMapper.fromSubCatRequest(request, subCatId);
		return subCategoryRepository.save(subCategory).getId();
	}

	public void delete(Integer subCatId) {
		subCategoryRepository.deleteById(subCatId);
	}
}
