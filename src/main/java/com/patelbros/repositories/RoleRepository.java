package com.patelbros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Optional<Role> findByRole(String role);
}
