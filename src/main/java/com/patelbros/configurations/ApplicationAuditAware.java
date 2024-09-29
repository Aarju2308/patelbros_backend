package com.patelbros.configurations;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.patelbros.entities.User;

public class ApplicationAuditAware implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || !authentication.isAuthenticated() || 	authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}
		
		User user = (User)authentication.getPrincipal();
		return Optional.of(user.getId());
	}

}
