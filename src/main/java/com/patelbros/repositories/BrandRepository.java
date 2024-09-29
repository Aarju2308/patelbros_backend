package com.patelbros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>{
	Optional<Brand> findByBrand(String brand);
	
	Optional<Brand> findByBrandAndIdNot(String brand,Integer brandId);
}
