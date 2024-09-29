package com.patelbros;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.patelbros.entities.Role;
import com.patelbros.entities.User;
import com.patelbros.repositories.RoleRepository;
import com.patelbros.repositories.UserRepository;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class PatelBrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatelBrosApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(RoleRepository roleRepository,UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return runner -> {
			if (roleRepository.findByRole("ROLE_USER").isEmpty()) {
				var role = Role.builder().role("ROLE_USER").build();
				roleRepository.save(role);
			}
			if (roleRepository.findByRole("ROLE_ADMIN").isEmpty()) {
				var adminRole = Role.builder().role("ROLE_ADMIN").build();
				roleRepository.save(adminRole);
			}
			if(userRepository.findByEmail("aarju@patelbros.com").isEmpty()) {
				Role adminRole = roleRepository.findByRole("ROLE_ADMIN").get();
				User user = User.builder()
						.firstName("Aarju")
						.lastName("Patel")
						.email("aarju@patelbros.com")
						.password(passwordEncoder.encode("A@rju2308"))
						.roles(List.of(adminRole))
						.enabled(true)
						.locked(false)
						.build();
				userRepository.save(user);
			}
			if(userRepository.findByEmail("admin@admin.com").isEmpty()) {
				Role adminRole = roleRepository.findByRole("ROLE_ADMIN").get();
				User user = User.builder()
						.firstName("John")
						.lastName("Doe")
						.email("admin@admin.com")
						.password(passwordEncoder.encode("admin@123"))
						.roles(List.of(adminRole))
						.enabled(true)
						.locked(false)
						.build();
				userRepository.save(user);
			}
			if(userRepository.findByEmail("test@patelbros.com").isEmpty()) {
				Role userRole = roleRepository.findByRole("ROLE_USER").get();
				User user = User.builder()
						.firstName("John")
						.lastName("Doe")
						.email("test@patelbros.com")
						.password(passwordEncoder.encode("test@123"))
						.roles(List.of(userRole))
						.enabled(true)
						.locked(false)
						.build();
				userRepository.save(user);
			}
			
		};
	}

}
