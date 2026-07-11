package com.coworking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.coworking.dto.auth.AuthenticationResponse;
import com.coworking.dto.auth.LoginRequest;
import com.coworking.dto.user.RegisterUserRequest;
import com.coworking.service.AuthenticationService;
import com.coworking.util.ApiPaths;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(
            @Valid @RequestBody RegisterUserRequest request) {

        return authenticationService.register(request);

    }

    @PostMapping("/login")
    public AuthenticationResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authenticationService.authenticate(request);

    }

}
