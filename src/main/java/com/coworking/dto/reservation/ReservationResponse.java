package com.coworking.dto.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.coworking.enums.ReservationStatus;

public record ReservationResponse(

        Long id,

        Long userId,

        String userName,

        Long spaceId,

        String spaceName,

        LocalDateTime startTime,

        LocalDateTime endTime,

        BigDecimal totalAmount,

        ReservationStatus status

) {
}
