package com.coworking.dto.space;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.coworking.enums.SpaceType;

public record SpaceResponse(

        Long id,

        String name,

        SpaceType type,

        Integer capacity,

        String location,

        BigDecimal hourlyRate,

        Boolean enabled,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}
