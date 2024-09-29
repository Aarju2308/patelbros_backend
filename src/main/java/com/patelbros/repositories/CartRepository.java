package com.patelbros.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Cart;
import com.patelbros.entities.Product;
import com.patelbros.entities.User;




@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	@Query("SELECT cart FROM Cart cart WHERE (cart.user = :user OR cart.uniqueId = :uniqueId) AND cart.product = :product")
	Optional<Cart> findByUserOrUniqueIdAndProduct(User user, int uniqueId, Product product);
	
	List<Cart> findByUniqueId(int uniqueId);
	
	long countByUniqueId(int uniqueId);
	
	long countByUser(User user);

	List<Cart> findByUser(User user);
		
	List<Cart> findByUserOrUniqueId(User user, int uniqueId);
}
