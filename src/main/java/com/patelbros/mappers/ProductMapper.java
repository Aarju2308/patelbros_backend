package com.patelbros.mappers;

import org.springframework.stereotype.Service;

import com.patelbros.dtos.ProductRequest;
import com.patelbros.dtos.ProductResponse;
import com.patelbros.entities.Brand;
import com.patelbros.entities.Product;
import com.patelbros.entities.ThirdCategory;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.BrandRepository;
import com.patelbros.repositories.ThirdCategoryRepository;
import com.patelbros.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMapper {
	
	private final ThirdCategoryRepository thirdCategoryRepository;
	private final BrandRepository brandRepository;
	private final ThirdCategoryMapper thirdCategoryMapper;
	private final BrandMapper brandMapper;
	private final FileUtils fileUtils;
	
	public Product toProduct(ProductRequest request) {
		
		ThirdCategory thirdCategory = thirdCategoryRepository.findById(request.getThirdCategoryId()).orElseThrow(
				()-> new OperationNotPermittedException("No Thirdcategory found at : "+ request.getThirdCategoryId())
			);
		
		Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(
				()-> new OperationNotPermittedException("No Brand found at : "+ request.getThirdCategoryId())
			);
		
		return Product.builder()
				.thirdCategory(thirdCategory)
				.brand(brand)
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.active(request.isActive())
				.build();
	}
	
	public ProductResponse toProductResponse(Product product) {
		return ProductResponse.builder()
				.thirdCategory(thirdCategoryMapper.toThirdCatResoponse(product.getThirdCategory()))
				.brand(brandMapper.toBrandResponse(product.getBrand()))
				.id(product.getId())
				.name(product.getName())
				.descrtiption(product.getDescription())
				.price(product.getPrice())
				.image(product.getPicture())
				.active(product.isActive())
				.rating(product.getRating())
				.build();
	}
}
