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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.patelbros.dtos.BrandRequest;
import com.patelbros.dtos.BrandResponse;
import com.patelbros.dtos.PageResponse;
import com.patelbros.services.BrandService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Brand")
public class BrandController {
	
	private final BrandService brandService;
	
	@PostMapping("/brand")
	public ResponseEntity<Integer> addBrand(
			@Valid @RequestBody BrandRequest request
	) {
		return ResponseEntity.ok(brandService.addBrand(request));
	}
	
	@GetMapping("/brand")
	public ResponseEntity<PageResponse<BrandResponse>> getBrandsPage(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size
	) {
		return ResponseEntity.ok(brandService.getBrandPage(page,size));
	}
	
	@GetMapping("/brand/{brandId}")
	public ResponseEntity<BrandResponse> getSingleBrand(@PathVariable Integer brandId) {
		return ResponseEntity.ok(brandService.getSingleBrand(brandId));
	}

	
	@PutMapping("brand/{brandId}")
	public ResponseEntity<Integer> updateBrand(@PathVariable Integer brandId, @Valid @RequestBody BrandRequest request) {
		
		return ResponseEntity.ok(brandService.updateBrand(brandId,request));
	}
	
	@DeleteMapping("brand/{brandId}")
	public ResponseEntity<?> deleteBrand(@PathVariable Integer brandId) {
		brandService.deleteBrand(brandId);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value  = "/logo/{brandId}", consumes = { "multipart/form-data" })
	public ResponseEntity<?> uploadBrandLogo (
			@PathVariable Integer brandId, 
			@RequestPart("file") MultipartFile file, 
			@Parameter
			Authentication authentication
			) {
		brandService.uploadBrandLogo(file, brandId);
		return ResponseEntity.accepted().build();
	}
	
	
	
}
