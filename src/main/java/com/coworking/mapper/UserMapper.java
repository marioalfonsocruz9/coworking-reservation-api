package com.coworking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coworking.dto.user.RegisterUserRequest;
import com.coworking.dto.user.UserResponse;
import com.coworking.model.User;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterUserRequest request);

    @Mapping(target = "fullName", expression = "java(user.getFullName())")
    UserResponse toResponse(User user);

}
