package com.coworking.dto.space;

import java.math.BigDecimal;

import com.coworking.enums.SpaceType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SpaceRequest(
        
        @NotBlank(message = "Name is required.")
        @Size(max = 100, message = "Name must not exceed 100 characters.")
        String name,
        
        @NotNull(message = "Space type is required.")
        SpaceType type,

        @NotNull(message = "Capacity is required.")
        @Min(value = 1, message = "Capacity must be greater than zero.")
        Integer capacity,

        @NotBlank(message = "Location is required.")
        @Size(max = 150, message = "Location must not exceed 150 characters.")
        String location,

        @NotNull(message = "Hourly rate is required.")
        @DecimalMin(value = "0.00", inclusive = false,
                message = "Hourly rate must be greater than zero.")
        @Digits(integer = 8, fraction = 2,
                message = "Hourly rate must have up to 8 integer digits and 2 decimals.")
        BigDecimal hourlyRate,

        @NotNull(message = "Enabled is required.")
        Boolean enabled

) {
}