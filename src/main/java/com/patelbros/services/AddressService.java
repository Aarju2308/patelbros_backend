package com.patelbros.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.patelbros.dtos.AddressRequest;
import com.patelbros.entities.Address;
import com.patelbros.entities.City;
import com.patelbros.entities.User;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.repositories.AddressRepository;
import com.patelbros.repositories.CityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
	
	private final AddressRepository addressRepository;
	private final CityRepository cityRepository;

	public List<Address> getAllAddress(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		return addressRepository.findByUser(user);
	}

	public Integer addAddress(AddressRequest request, Authentication authentication) {
		
		City city = cityRepository.findById(request.getCityId()).orElseThrow(
				()->new OperationNotPermittedException("No city found with id : "+request.getCityId())
			);
		
		User user = (User)authentication.getPrincipal();
		
		Address address = Address.builder()
				.user(user)
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.address(request.getAddress())
				.city(city)
				.build();
		
		return addressRepository.save(address).getId();
	}

	public Address getSingleAddress(Integer addressId) {
		
		return addressRepository.findById(addressId).orElseThrow(
				()->new OperationNotPermittedException("No Address found with id : "+addressId)
				);
	}

	public Integer updateAddress(Integer addressId, AddressRequest request, Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		
		City city = cityRepository.findById(request.getCityId()).orElseThrow(
				()->new OperationNotPermittedException("No city found with id : "+request.getCityId())
			);
		
		Address oldAddress =  addressRepository.findById(addressId).orElseThrow(
				()->new OperationNotPermittedException("No Address found with id : "+addressId)
			);
		
		if (oldAddress.getUser() != user) {
			throw new OperationNotPermittedException("You cannot modify this address : "+addressId);
		}
		
		oldAddress.setFirstName(request.getFirstName());
		oldAddress.setLastName(request.getLastName());
		oldAddress.setAddress(request.getAddress());
		oldAddress.setCity(city);
		return addressRepository.save(oldAddress).getId();
	}

	public void deleteAddress(Integer addressId, Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		
		
		Address oldAddress =  addressRepository.findById(addressId).orElseThrow(
				()->new OperationNotPermittedException("No Address found with id : "+addressId)
			);
		
		if (oldAddress.getUser() != user) {
			throw new OperationNotPermittedException("You cannot modify this address : "+addressId);
		}
		
		addressRepository.deleteById(addressId);
	}
	
}