package com.patelbros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.patelbros.entities.City;
import com.patelbros.entities.State;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.CityRepository;
import com.patelbros.repositories.StateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityService {
	private final CityRepository cityRepository;
	private final StateRepository stateRepository;
	
	public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Optional<City> findById(Integer id) {
        return cityRepository.findById(id);
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public void deleteById(Integer id) {
        cityRepository.deleteById(id);
    }

	public List<City> findAllByCountry(Integer stateId) {
		State state = stateRepository.findById(stateId).orElseThrow(
					()-> new OperationNotPermittedException("No State Found With Id : "+stateId)
				);
		return cityRepository.findByState(state);
	}
}
