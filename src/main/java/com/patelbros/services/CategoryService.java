package com.patelbros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.patelbros.dtos.CategoryRequest;
import com.patelbros.dtos.CategoryResponse;
import com.patelbros.dtos.PageResponse;
import com.patelbros.entities.Category;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public Integer save(CategoryRequest category) {
		var tempCat = categoryRepository.findByCategory(category.getCategory());
		
		if (tempCat.isPresent()) {
			throw new OperationNotPermittedException(category.getCategory()	 + " Already Exists");
		} else {
			var cat = Category.builder().id(null).category(category.getCategory()).active(category.isActive()).build(); 
			return categoryRepository.save(cat).getId();
		}	
	}

	public PageResponse<CategoryResponse> findAllCategories(int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		
		Page<Category> categories = categoryRepository.findAll(pageable); 
		
		List<CategoryResponse> categoryResponses = categories.stream()
				.map(c -> CategoryResponse.builder().id(c.getId()).category(c.getCategory()).active(c.isActive()).build())
				.toList();
		
		return new PageResponse<CategoryResponse>(
					categoryResponses,
					categories.getNumber(),
					categories.getSize(),
					categories.getTotalElements(),
					categories.getTotalPages(),
					categories.isFirst(),
					categories.isLast()
				);
	}

	public CategoryResponse getCategoryById(Integer catId) {
		Category category = categoryRepository.findById(catId).orElseThrow(
					() -> new OperationNotPermittedException("Category With Id Does Not Exist")
				);
		return CategoryResponse.builder().id(category.getId()).category(category.getCategory()).active(category.isActive()).build();
	}

	public Integer updateCategory(Integer catId, CategoryRequest request) {
		Optional<Category> temp = categoryRepository.findByCategoryAndIdNot(request.getCategory(), catId);
		if (temp.isPresent()) {
			throw new OperationNotPermittedException("Category Already Exists");
		}
		var cat = Category.builder()
				.id(catId)
				.category(request.getCategory())
				.active(request.isActive())
				.build();
		
		return categoryRepository.save(cat).getId();
	}

	public void deleteCategory(Integer catId) {
		var cat = categoryRepository.findById(catId);
		
		if (cat.isEmpty()) {
			throw new OperationNotPermittedException("No category found with id : " + catId);
		}
		
		categoryRepository.delete(cat.get());
	}

}