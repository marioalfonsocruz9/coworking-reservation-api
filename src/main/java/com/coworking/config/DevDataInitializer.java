package com.coworking.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coworking.enums.Role;
import com.coworking.model.User;
import com.coworking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevDataInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initAdmin() {

        return args -> {

            if (userRepository.existsByEmail("admin@coworking.com")) {
                return;
            }
            
            User admin = new User(
                    "System",
                    "Admin",
                    "admin@coworking.com",
                    passwordEncoder.encode("MyAdmin99*"),
                    Role.ROLE_ADMIN
            );
            
            userRepository.save(admin);

            log.info("Admin user created.");
        };
    }

}
