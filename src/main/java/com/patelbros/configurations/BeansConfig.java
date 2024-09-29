package com.patelbros.configurations;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class BeansConfig {
	
	@Value("${spring.security.origins}")
	private ArrayList<String> allowedOrigins;

	@Bean
	AuthenticationProvider authenticationProvider(UserDetailsService details) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(details);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	AuditorAware<Integer> auditorAware(){
		return new ApplicationAuditAware();
	}
	
	@Bean
	CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(allowedOrigins);
		configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.ORIGIN,HttpHeaders.CONTENT_TYPE,HttpHeaders.AUTHORIZATION,HttpHeaders.ACCEPT));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH"));
		corsConfigurationSource.registerCorsConfiguration("/**", configuration);
		return new CorsFilter(corsConfigurationSource);
	}
	
}