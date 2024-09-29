package com.patelbros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.User;



@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	
	Optional<User> findByPhoneAndIdNot(String phone, Integer id);
	
	Optional<User> findByEmailAndIdNot(String email, Integer id);
	
}
