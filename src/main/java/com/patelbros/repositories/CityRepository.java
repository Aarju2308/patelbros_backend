package com.patelbros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.City;
import com.patelbros.entities.State;

import java.util.List;


@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
	List<City> findByState(State state);
}
