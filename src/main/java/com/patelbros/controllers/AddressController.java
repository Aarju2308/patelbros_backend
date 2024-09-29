package com.patelbros.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patelbros.dtos.AddressRequest;
import com.patelbros.entities.Address;
import com.patelbros.services.AddressService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/user/address")
@RequiredArgsConstructor
@Tag(name="Address")
public class AddressController {
	
	private final AddressService addressService;
	
	@GetMapping("/")
	public ResponseEntity<List<Address>> getAllAddress(Authentication authentication) {
		return ResponseEntity.ok(addressService.getAllAddress(authentication));
	}
	
	@GetMapping("/{addressId}")
	public ResponseEntity<Address> getSingleAddress(@PathVariable Integer addressId) {
		return ResponseEntity.ok(addressService.getSingleAddress(addressId));
	}
	
	
	@PostMapping("/")
	public ResponseEntity<Integer> addAddress(@Valid @RequestBody AddressRequest request,Authentication authentication) {
		return ResponseEntity.ok(addressService.addAddress(request,authentication));
	}
	
	@PutMapping("/{addressId}")
	public ResponseEntity<Integer> updateAddress(@PathVariable Integer addressId, @RequestBody AddressRequest request, Authentication authentication) {
		return ResponseEntity.ok(addressService.updateAddress(addressId,request,authentication));
	}

	@DeleteMapping("/{addressId}")
	public ResponseEntity<?> deleteAddress(@PathVariable Integer addressId, Authentication authentication) {
		addressService.deleteAddress(addressId,authentication);
		return ResponseEntity.ok().build();
	}
	
	

}