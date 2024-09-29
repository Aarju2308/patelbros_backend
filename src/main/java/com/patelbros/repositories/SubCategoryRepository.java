package com.patelbros.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Category;
import com.patelbros.entities.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

//	@Query("""
//			SELECT subCat 
//			FROM SubCategory subCat
//			WHERE subCat.Category = :cat
//			AND subCat.subCategory = :subCategory
//			""")
//	Optional<SubCategory> getSubCategoryByCategory(Category cat, String subCategory);

	List<SubCategory> findByCategory(Category category);
	
	Optional<SubCategory> findByCategoryAndSubCategory(Category category, String subCategory);
	
	Optional<SubCategory> findBySubCategoryAndIdNot(String subCategory, Integer id);
}
