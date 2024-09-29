package com.patelbros.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patelbros.dtos.SubCategoryRequest;
import com.patelbros.dtos.SubCategoryResponse;
import com.patelbros.services.SubCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "SubCategory")
public class SubCategoryController {
	
	private final SubCategoryService subCategoryService;
	
	@PostMapping("/subCategory")
	public ResponseEntity<Integer> createSubCategory(@RequestBody @Valid SubCategoryRequest request) {
		
		return ResponseEntity.ok(subCategoryService.save(request));
	}
	
	@GetMapping("/subCategory/{subCatId}")
	public ResponseEntity<SubCategoryResponse> getSingleSubCategory (@PathVariable Integer subCatId) {
		return ResponseEntity.ok(subCategoryService.getSingleSubCategory(subCatId));
	}
	
	@PutMapping("subCategory/{subCatId}")
	public ResponseEntity<?> updateSubCategory(@PathVariable Integer subCatId, @Valid @RequestBody SubCategoryRequest request) {
		return ResponseEntity.ok(subCategoryService.update(request,subCatId));
	}
	
	@DeleteMapping("subCategory/{subCatId}")
	public ResponseEntity<?> deleteSubCategory(@PathVariable Integer subCatId) {
		subCategoryService.delete(subCatId);
		return ResponseEntity.ok().build();
	}
	
}
