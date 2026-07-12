package com.coworking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.coworking.dto.space.SpaceRequest;
import com.coworking.dto.space.SpaceResponse;
import com.coworking.model.Space;

@Mapper(componentModel = "spring")
public interface SpaceMapper {

    Space toEntity(SpaceRequest request);

    SpaceResponse toResponse(Space entity);

    void updateEntity(SpaceRequest request, @MappingTarget Space entity);

}