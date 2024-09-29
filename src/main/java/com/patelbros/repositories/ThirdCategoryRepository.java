package com.patelbros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.SubCategory;
import com.patelbros.entities.ThirdCategory;
import java.util.List;


@Repository
public interface ThirdCategoryRepository extends JpaRepository<ThirdCategory, Integer>{
	
	Optional<ThirdCategory> findBySubCategoryAndThirdCategory(SubCategory subCategory, String thirdCategory);
	
	Optional<ThirdCategory> findByThirdCategoryAndIdNot(String thirdCategory,Integer thirdCatId);
	
	List<ThirdCategory> findBySubCategory(SubCategory subCategory);
}
