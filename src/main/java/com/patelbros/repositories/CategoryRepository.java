package com.patelbros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Category;



@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findByCategory(String category);
	
	Optional<Category> findByCategoryAndIdNot(String category,Integer catId);
}
