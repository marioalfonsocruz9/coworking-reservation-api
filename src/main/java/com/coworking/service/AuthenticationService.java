package com.coworking.service;

import com.coworking.dto.auth.AuthenticationResponse;
import com.coworking.dto.auth.LoginRequest;
import com.coworking.dto.user.RegisterUserRequest;

public interface AuthenticationService {
	
	AuthenticationResponse register(RegisterUserRequest request);

    AuthenticationResponse authenticate(LoginRequest request);
    
}
