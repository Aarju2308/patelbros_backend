package com.patelbros.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patelbros.dtos.CategoryRequest;
import com.patelbros.dtos.CategoryResponse;
import com.patelbros.services.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

	private final CategoryService categoryService;
	
	
	@GetMapping("/")
	public String getMethodName(Authentication authentication) {
		return "Hello " + authentication.getName();
	}
	
	@PostMapping("/category")
	public ResponseEntity<Integer> createCategory(@RequestBody @Valid CategoryRequest category) {
		System.out.println(category);
		return ResponseEntity.ok(categoryService.save(category));
	}
	
	@GetMapping("/category/{catId}")
	public ResponseEntity<CategoryResponse> getSingleCategory(@PathVariable Integer catId) {
		return ResponseEntity.ok(categoryService.getCategoryById(catId));
	}
	
	@PutMapping("/category/{catId}")
	public ResponseEntity<Integer> updateCategory(@PathVariable Integer catId, @RequestBody CategoryRequest request) {
		
		return ResponseEntity.ok(categoryService.updateCategory(catId,request));
	}
	
	@DeleteMapping("/category/{catId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer catId){
		categoryService.deleteCategory(catId);
		return ResponseEntity.ok().build();
	}
}
