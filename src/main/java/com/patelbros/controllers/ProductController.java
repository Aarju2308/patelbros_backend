package com.patelbros.controllers;

import org.springframework.http.ResponseEntity;
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

import com.patelbros.dtos.ProductRequest;
import com.patelbros.dtos.ProductResponse;
import com.patelbros.services.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {
	private final ProductService productService;
	
	@PostMapping("/product")
	public ResponseEntity<Integer> addProduct(@Valid @RequestBody ProductRequest request) {
		
		return ResponseEntity.ok(productService.addProduct(request));
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<ProductResponse> getSingleProduct(@PathVariable Integer productId) {
		return ResponseEntity.ok(productService.getSingleProduct(productId));
	}
	
	@PutMapping("product/{productId}")
	public ResponseEntity<Integer> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductRequest request) {
		
		return ResponseEntity.ok(productService.updateProduct(productId,request));
	}
	
	@DeleteMapping("product/{productId}")
	public ResponseEntity<Integer> deleteProduct(@PathVariable Integer productId) {
		
		productService.deleteProduct(productId);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/productImage/{productId}", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadProductImage(@RequestParam Integer productId,@RequestPart("file") MultipartFile file) {
		productService.uploadProdutImage(file, productId);
		return ResponseEntity.ok().build();
	}
	
	
}