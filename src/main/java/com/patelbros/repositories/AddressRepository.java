package com.patelbros.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Address;
import com.patelbros.entities.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
	List<Address> findByUser(User user);
}