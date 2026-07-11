package com.coworking.dto.auth;

import com.coworking.dto.user.UserResponse;

public record AuthenticationResponse(
        String token,
        UserResponse user
) {
}
