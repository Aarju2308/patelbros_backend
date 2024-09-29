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

import com.patelbros.dtos.ThirdCategoryRequest;
import com.patelbros.dtos.ThirdCategoryResponse;
import com.patelbros.services.ThirdCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "ThirdCategory")
public class ThirdCategoryController {
	private final ThirdCategoryService thirdCategoryService;

	@PostMapping("/thirdCategory")
	public ResponseEntity<Integer> save(@Valid @RequestBody ThirdCategoryRequest request) {
		
		return ResponseEntity.ok(thirdCategoryService.addThirdCategory(request));
	}
	
	
	@GetMapping("/thirdCategory/{thirdCatId}")
	public ResponseEntity<ThirdCategoryResponse> getSingleThirdCat(@PathVariable Integer thirdCatId) {
		return ResponseEntity.ok(thirdCategoryService.getSingleThirdCategory(thirdCatId));
	}

	
	@PutMapping("/thirdCategory/{thirdCatId}")
	public ResponseEntity<Integer> updateThirdCategory(@PathVariable Integer thirdCatId, @Valid @RequestBody ThirdCategoryRequest request) {
		
		return ResponseEntity.ok(thirdCategoryService.updateThirdCategory(thirdCatId,request));
	}
	
	@DeleteMapping("/thirdCategory/{thirdCatId}")
	public ResponseEntity<Integer> deleteThirdCategory(@PathVariable Integer thirdCatId) {
		thirdCategoryService.deleteThirdCategory(thirdCatId);
		return ResponseEntity.ok().build();
	}
	
	
	

}