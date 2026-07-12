package com.coworking.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coworking.dto.auth.AuthenticationResponse;
import com.coworking.dto.auth.LoginRequest;
import com.coworking.dto.user.RegisterUserRequest;
import com.coworking.enums.Role;
import com.coworking.exception.EmailAlreadyExistsException;
import com.coworking.mapper.UserMapper;
import com.coworking.model.User;
import com.coworking.security.jwt.JwtService;
import com.coworking.security.user.CoworkingUserDetails;
import com.coworking.service.AuthenticationService;
import com.coworking.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterUserRequest request) {

        if (userService.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = userMapper.toEntity(request);

        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(Role.ROLE_USER);
        user.setEnabled(true);

        User savedUser = userService.save(user);

        String token = jwtService.generateToken(savedUser);

        return new AuthenticationResponse(
                token,
                userMapper.toResponse(savedUser)
        );

    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        CoworkingUserDetails principal =
                (CoworkingUserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(principal.getUser());

        return new AuthenticationResponse(
                token,
                userMapper.toResponse(principal.getUser()));

    }

}
