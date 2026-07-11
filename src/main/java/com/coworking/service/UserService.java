package com.coworking.service;

import java.util.Optional;

import com.coworking.model.User;

public interface UserService {
	
	User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);
    
}
