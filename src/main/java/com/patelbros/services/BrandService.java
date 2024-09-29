package com.patelbros.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.patelbros.dtos.BrandRequest;
import com.patelbros.dtos.BrandResponse;
import com.patelbros.dtos.PageResponse;
import com.patelbros.entities.Brand;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.mappers.BrandMapper;
import com.patelbros.repositories.BrandRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {
	private final BrandRepository brandRepository;
	private final FileStorageService fileStorageService;
	private final BrandMapper brandMapper;
	
	public Integer addBrand(BrandRequest request) {
		
		var temp = brandRepository.findByBrand(request.getBrand());
		if (temp.isPresent()) {
			throw new OperationNotPermittedException("Brand : " + request.getBrand() + " Already Exists ! ");
		}
		
		Brand brand = Brand.builder()
				.brand(request.getBrand())
				.active(request.isActive())
				.build();
		
		return brandRepository.save(brand).getId();
	}
	
	public void uploadBrandLogo(MultipartFile file, Integer brandId) {
		var brand = brandRepository.findById(brandId).orElseThrow(
				()->new OperationNotPermittedException("No brand found with id : "+brandId)
				);
		
		System.out.println("Adding Logo");
		String logoPath = fileStorageService.saveFile(file,"brands");
		brand.setLogo(logoPath);
		brandRepository.save(brand);
	} 

	public PageResponse<BrandResponse> getBrandPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		Page<Brand> brands = brandRepository.findAll(pageable);
		
		List<BrandResponse> brandResponses = brands.stream()
				.map(b->brandMapper.toBrandResponse(b)).toList();
		
		return new PageResponse<BrandResponse>(
				brandResponses,
				brands.getNumber(),
				brands.getSize(),
				brands.getTotalElements(),
				brands.getTotalPages(),
				brands.isFirst(),
				brands.isLast()
				);
	}

	public BrandResponse getSingleBrand(Integer brandId) {
		Brand brand = brandRepository.findById(brandId).orElseThrow(
					()-> new OperationNotPermittedException("No Brand Found with id : "+brandId)
				);
		
		return brandMapper.toBrandResponse(brand);
	}

	public List<BrandResponse> getAllBrands() {
		List<Brand> brands = brandRepository.findAll();
		return brands.stream().map(b->brandMapper.toBrandResponse(b)).toList();
	}

	public Integer updateBrand(Integer brandId, @Valid BrandRequest request) {
		
		Brand brand = brandRepository.findById(brandId).orElseThrow(
				()-> new OperationNotPermittedException("No Brand Found with id : "+brandId)
			);

		var temp = brandRepository.findByBrandAndIdNot(request.getBrand(),brandId);
		if (temp.isPresent()) {
			throw new OperationNotPermittedException("Brand Already Exists with name: "+request.getBrand());
		}
		
		brand.setBrand(request.getBrand());
		brand.setActive(request.isActive());
		return brandRepository.save(brand).getId();
	}

	public void deleteBrand(Integer brandId) {
		brandRepository.deleteById(brandId);
	}	
}
