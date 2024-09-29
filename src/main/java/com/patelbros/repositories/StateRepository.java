package com.patelbros.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Country;
import com.patelbros.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer>{
	List<State> findByCountry(Country country);
}
