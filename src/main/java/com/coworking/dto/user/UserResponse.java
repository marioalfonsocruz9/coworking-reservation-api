package com.coworking.dto.user;

import com.coworking.enums.Role;

public record UserResponse(

    Long id,

    String firstName,

    String lastName,

    String fullName,

    String email,

    Role role,

    Boolean enabled

) {
}