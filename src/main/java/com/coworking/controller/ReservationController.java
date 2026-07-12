package com.coworking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.coworking.dto.reservation.ReservationRequest;
import com.coworking.dto.reservation.ReservationResponse;
import com.coworking.service.ReservationService;
import com.coworking.util.ApiPaths;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPaths.RESERVATIONS)
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(
            @Valid @RequestBody ReservationRequest request) {

        return reservationService.create(request);
    }

    @GetMapping("/me")
    public List<ReservationResponse> findMyReservations() {

        return reservationService.findMyReservations();
    }

    @GetMapping
    public List<ReservationResponse> findAll() {

        return reservationService.findAll();
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {

        reservationService.cancel(id);
    }

}
