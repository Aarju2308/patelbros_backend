package com.patelbros.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patelbros.dtos.BrandResponse;
import com.patelbros.dtos.CartRequest;
import com.patelbros.dtos.CartResponse;
import com.patelbros.dtos.CategoryResponse;
import com.patelbros.dtos.PageResponse;
import com.patelbros.dtos.ProductResponse;
import com.patelbros.dtos.SubCategoryResponse;
import com.patelbros.dtos.ThirdCategoryResponse;
import com.patelbros.entities.City;
import com.patelbros.entities.Country;
import com.patelbros.entities.State;
import com.patelbros.services.BrandService;
import com.patelbros.services.CartService;
import com.patelbros.services.CategoryService;
import com.patelbros.services.CityService;
import com.patelbros.services.CountryService;
import com.patelbros.services.ProductService;
import com.patelbros.services.StateService;
import com.patelbros.services.SubCategoryService;
import com.patelbros.services.ThirdCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Tag(name="Public")
public class PublicController {
	private final CategoryService categoryService;
	private final SubCategoryService subCategoryService;
	private final ThirdCategoryService thirdCategoryService;
	private final BrandService brandService;
	private final ProductService productService;
	private final CartService cartService;
	private final CountryService countryService;
	private final StateService stateService;
	private final CityService cityService;
	
	@GetMapping("/category")
	public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(
				@RequestParam(name = "page", required = false, defaultValue = "0") int page,
				@RequestParam(name = "size", required = false, defaultValue = "10") int size
			) {
		return ResponseEntity.ok(categoryService.findAllCategories(page, size));
	}
	
	@GetMapping("/subCategory")
	public ResponseEntity<PageResponse<SubCategoryResponse>> getSubCategyPage(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size
	) {
		return ResponseEntity.ok(subCategoryService.getSubCategyPage(page,size));
	}
	
	@GetMapping("/subCategories")
	public ResponseEntity<List<SubCategoryResponse>> getAllSubCategories() {
		return ResponseEntity.ok(subCategoryService.getAllSubCategories());
	}
	
	@GetMapping("/subCategoryByCategory/{catId}")
	public ResponseEntity<List<SubCategoryResponse>> getAllSubCategoriesByCategory(@PathVariable Integer catId) {
		return ResponseEntity.ok(subCategoryService.getAllSubCategoriesByCategory(catId));
	}
	
	@GetMapping("/thirdCategory")
	public ResponseEntity<PageResponse<ThirdCategoryResponse>> getAllThirdCategories(
				@RequestParam(name = "size", required = false, defaultValue = "10") int size,
				@RequestParam(name = "page", required = false, defaultValue = "0") int page
			) {
		
		return ResponseEntity.ok(thirdCategoryService.getAllThirdCategories(page,size));
	}
	
	@GetMapping("/thirdCategoryBySubCategory/{subCatId}")
	public ResponseEntity<List<ThirdCategoryResponse>> getThirdCategoryBySubCategory(@PathVariable Integer subCatId) {
		return ResponseEntity.ok(thirdCategoryService.getAllThirdCategoriesBySubCat(subCatId));
	}
	
	@GetMapping("/brands")
	public ResponseEntity<List<BrandResponse>> getAllBrands() {
		return ResponseEntity.ok(brandService.getAllBrands());
	}
	
	@GetMapping("/product")
	public ResponseEntity<PageResponse<ProductResponse>> getProductPage(
			@RequestParam(name = "page", required = false, defaultValue = "0")int page,
			@RequestParam(name = "size", required = false, defaultValue = "10")int size,
			@RequestParam(name = "filter", required = false, defaultValue = "0") int filter	
			) {
		return ResponseEntity.ok(productService.getProductsPage(page,size));
	}
	
	@GetMapping("/product/{thirdCatId}")
	public ResponseEntity<PageResponse<ProductResponse>> getProductPageByThirdCategory(@PathVariable Integer thirdCatId) {
		return ResponseEntity.ok(productService.getProductPageByThirdCategory(thirdCatId));
	}
	
	@GetMapping("/productsBySubCategory/{subCatId}")
	public ResponseEntity<PageResponse<ProductResponse>> getProductsPageBySubCategory(
			@PathVariable Integer subCatId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "filter", required = false, defaultValue = "0") int filter	
	) {
		return ResponseEntity.ok(productService.getProductsPageBySubCategory(subCatId,page,size,filter));
	}
	
	@PostMapping("/productsByThirdCategories")
	public ResponseEntity<PageResponse<ProductResponse>> getProductsPageByThirdCategories(
			@RequestBody List<Integer> thirdCats,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "filter", required = false, defaultValue = "0") int filter		
	) {
		return ResponseEntity.ok(productService.getProductsPageByThirdCategories(thirdCats,page,size,filter));
	}
	
	@GetMapping("/searchProducts")
	public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name) {
		return ResponseEntity.ok(productService.searchProducts(name));
	}
	
	@GetMapping("/singleProduct/{productId}")
	public ResponseEntity<ProductResponse> getSinglePublicProduct(@PathVariable Integer productId) {
		return ResponseEntity.ok(productService.getSingleProduct(productId));
	}
	
	@PostMapping("/cart")
	public ResponseEntity<Integer> addToCart(
			@RequestBody CartRequest request
	) {
		
		return ResponseEntity.ok(cartService.addToCart(request,null));
	}
	
	@GetMapping("/countCart")
	public ResponseEntity<Long> getCartCountByUniqueId(@RequestParam int uniqueId) {
		return ResponseEntity.ok(cartService.getCartCountByUniqueId(uniqueId));
	}
	
	@GetMapping("/cart")
	public ResponseEntity<List<CartResponse>> getCartByUniqueId(
			@RequestParam int uniqueId
			) {
		return ResponseEntity.ok(cartService.getCartByUniqueId(uniqueId));
	}
	
	@GetMapping("/country")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.findAll());
    }
	
	@GetMapping("/state/{countryId}")
    public ResponseEntity<List<State>> getAllStatesByCountry(@PathVariable Integer countryId) {
        return ResponseEntity.ok(stateService.findAllByCountry(countryId));
    }
	
	@GetMapping("/city/{stateId}")
    public ResponseEntity<List<City>> getAllCitiesByState(@PathVariable Integer stateId) {
        return ResponseEntity.ok(cityService.findAllByCountry(stateId));
    }
}