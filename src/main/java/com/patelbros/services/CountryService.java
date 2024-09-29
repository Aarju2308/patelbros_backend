package com.patelbros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.patelbros.entities.Country;
import com.patelbros.repositories.CountryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {
	private final CountryRepository countryRepository;
	
	public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public Optional<Country> findById(Integer id) {
        return countryRepository.findById(id);
    }

    public Country save(Country country) {
        return countryRepository.save(country);
    }

    public void deleteById(Integer id) {
        countryRepository.deleteById(id);
    }

}
