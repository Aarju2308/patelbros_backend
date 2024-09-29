package com.patelbros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.patelbros.entities.Country;
import com.patelbros.entities.State;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.CountryRepository;
import com.patelbros.repositories.StateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StateService {
	private final StateRepository stateRepository;
	private final CountryRepository countryRepository;
	
	public List<State> findAll() {
        return stateRepository.findAll();
    }

    public Optional<State> findById(Integer id) {
        return stateRepository.findById(id);
    }

    public State save(State state) {
        return stateRepository.save(state);
    }

    public void deleteById(Integer id) {
        stateRepository.deleteById(id);
    }

	public List<State> findAllByCountry(Integer countryId) {
		Country country = countryRepository.findById(countryId).orElseThrow(
					()-> new OperationNotPermittedException("No Country Found with id : "+ countryId)
				);
		return stateRepository.findByCountry(country);
	}
}
