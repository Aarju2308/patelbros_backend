package com.patelbros.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Product;
import com.patelbros.entities.ThirdCategory;




@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	Optional<Product> findByNameAndThirdCategory(String name, ThirdCategory category);
	
	Optional<Product> findByNameAndIdNot(String name,Integer id);
	
	Page<Product> findByThirdCategory(ThirdCategory thirdCategory, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.thirdCategory.id IN :thirdCats")
	Page<Product> findByThirdCategoryIds(List<Integer> thirdCats, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
	List<Product> searchProducts(String name);
}
