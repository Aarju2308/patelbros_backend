package com.patelbros.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.patelbros.dtos.PageResponse;
import com.patelbros.dtos.ProductRequest;
import com.patelbros.dtos.ProductResponse;
import com.patelbros.entities.Brand;
import com.patelbros.entities.Product;
import com.patelbros.entities.SubCategory;
import com.patelbros.entities.ThirdCategory;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.mappers.ProductMapper;
import com.patelbros.repositories.BrandRepository;
import com.patelbros.repositories.ProductRepository;
import com.patelbros.repositories.SubCategoryRepository;
import com.patelbros.repositories.ThirdCategoryRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	private final FileStorageService fileStorageService;
	private final ThirdCategoryRepository thirdCategoryRepository;
	private final ProductMapper productMapper;
	private final BrandRepository brandRepository;
	private final SubCategoryRepository subCategoryRepository;
	

	public Integer addProduct(@Valid ProductRequest request) {
		
		ThirdCategory thirdCategory = thirdCategoryRepository.findById(request.getThirdCategoryId()).orElseThrow(
				()->new OperationNotPermittedException("No thirdCategory Found with id " + request.getThirdCategoryId())
				);
		
		var test = productRepository.findByNameAndThirdCategory(request.getName(), thirdCategory);
		
		if (test.isPresent()) {
			throw new OperationNotPermittedException("Product " + request.getName() + " Already exists in category :"+thirdCategory.getThirdCategory());
		}
		
		Product product = productMapper.toProduct(request);
		product.setPicture(null);
		
		return productRepository.save(product).getId();
	}
	
	public void uploadProdutImage(MultipartFile file, Integer productId) {
		Product product = productRepository.findById(productId).orElseThrow(
					()-> new OperationNotPermittedException("No Product found with id : "+productId)
				);
		String filePath = fileStorageService.saveFile(file,"products");
		product.setPicture(filePath);
		
		productRepository.save(product);
	}

	public PageResponse<ProductResponse> getProductsPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size,Sort.by("createdAt").ascending());
		
		Page<Product> products = productRepository.findAll(pageable);
		List<ProductResponse> productResponse = products.stream()
				.map(p-> productMapper.toProductResponse(p))
				.toList();
		
		return new PageResponse<ProductResponse>(
					productResponse,
					products.getNumber(),
					products.getSize(),
					products.getTotalElements(),
					products.getTotalPages(),
					products.isFirst(),
					products.isLast()
					
				);
	}

	public ProductResponse getSingleProduct(Integer productId) {
		
		Product product = productRepository.findById(productId).orElseThrow(
				()-> new OperationNotPermittedException("No Product found with id : "+productId)
			);
		
		return productMapper.toProductResponse(product);
	}

	public PageResponse<ProductResponse> getProductPageByThirdCategory(Integer thirdCatId) {
		

		ThirdCategory thirdCategory = thirdCategoryRepository.findById(thirdCatId).orElseThrow(
				()-> new OperationNotPermittedException("No Thirdcategory found at : "+ thirdCatId)
			);
		
		
		Pageable pageable = PageRequest.of(0, 10);
		Page<Product> products = productRepository.findByThirdCategory(thirdCategory, pageable);
		
		List<ProductResponse> productResponse = products.stream()
				.map(p-> productMapper.toProductResponse(p))
				.toList();
		
		return new PageResponse<ProductResponse>(
					productResponse,
					products.getNumber(),
					products.getSize(),
					products.getTotalElements(),
					products.getTotalPages(),
					products.isFirst(),
					products.isLast()
					
				);
	}

	public Integer updateProduct(Integer productId, @Valid ProductRequest request) {

		Product product = productRepository.findById(productId).orElseThrow(
				()-> new OperationNotPermittedException("No Product found with id : "+productId)
			);
		
		ThirdCategory thirdCategory = thirdCategoryRepository.findById(request.getThirdCategoryId()).orElseThrow(
				()-> new OperationNotPermittedException("No Thirdcategory found at : "+ request.getThirdCategoryId())
			);
		
		Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(
				()-> new OperationNotPermittedException("No Brand found at : "+ request.getThirdCategoryId())
			);
		
		var test = productRepository.findByNameAndIdNot(request.getName(), productId);
		
		if (test.isPresent()) {
			throw new OperationNotPermittedException("Product With name already present : "+ request.getName());
		}
		
		product.setId(productId);
		product.setThirdCategory(thirdCategory);
		product.setBrand(brand);
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setActive(request.isActive());
		
		return productRepository.save(product).getId();		
	}

	public void deleteProduct(Integer productId) {
		productRepository.deleteById(productId);
	}

	public PageResponse<ProductResponse> getProductsPageByThirdCategories(List<Integer> thirdCats, int page, int size, int filter) {
		
		Sort filt = Sort.by("createdAt").ascending();
		
		switch (filter) {
		case 1: {
			filt = Sort.by("name").ascending();
			break;
		}
		case 2:{
			filt = Sort.by("name").descending();
			break;
		}
		case 3:{
			filt = Sort.by("price").ascending();
			break;
		}
		case 4:{
			filt = Sort.by("price").descending();
			break;
		}
		default:
			filt = Sort.by("createdAt").ascending();
		}
		
		Pageable pageable = PageRequest.of(page, size,filt);
		
		Page<Product> products = productRepository.findByThirdCategoryIds(thirdCats,pageable);
		
		List<ProductResponse> productResponse = products.stream()
				.map(p-> productMapper.toProductResponse(p))
				.toList();
		
		return new PageResponse<ProductResponse>(
					productResponse,
					products.getNumber(),
					products.getSize(),
					products.getTotalElements(),
					products.getTotalPages(),
					products.isFirst(),
					products.isLast()
					
				);
	}

	public PageResponse<ProductResponse> getProductsPageBySubCategory(Integer subCatId, int page, int size, int filter) {

		SubCategory subCategory = subCategoryRepository.findById(subCatId).orElseThrow(
					()->new OperationNotPermittedException("No SubCategory Found with id: "+subCatId)
				);
		
		List<ThirdCategory> thirdCategoryResponses = thirdCategoryRepository.findBySubCategory(subCategory);
		
		List<Integer> thirdCatIds = thirdCategoryResponses.stream().map(t->t.getId()).toList();
		
		
		return getProductsPageByThirdCategories(thirdCatIds,page,size,filter);
	}

	public List<ProductResponse> searchProducts(String name) {
		List<Product> products = productRepository.searchProducts(name);
		
		return products.stream().map(p -> productMapper.toProductResponse(p)).toList();
	}
	
}
