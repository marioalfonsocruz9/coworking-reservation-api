package com.coworking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coworking.dto.reservation.ReservationResponse;
import com.coworking.model.Reservation;

@Mapper(config = CentralMapperConfig.class)
public interface ReservationMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", expression = "java(reservation.getUser().getFullName())")
    @Mapping(target = "spaceId", source = "space.id")
    @Mapping(target = "spaceName", source = "space.name")
    ReservationResponse toResponse(Reservation reservation);

}
