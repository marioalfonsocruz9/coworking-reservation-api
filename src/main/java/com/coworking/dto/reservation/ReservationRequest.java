package com.coworking.dto.reservation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(

        @NotNull(message = "Space is required.")
        Long spaceId,

        @NotNull(message = "Start time is required.")
        @Future(message = "Start time must be in the future.")
        LocalDateTime startTime,

        @NotNull(message = "End time is required.")
        @Future(message = "End time must be in the future.")
        LocalDateTime endTime

) {
}
