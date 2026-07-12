package com.coworking.service;

import java.util.List;

import com.coworking.dto.reservation.ReservationRequest;
import com.coworking.dto.reservation.ReservationResponse;

public interface ReservationService {

    ReservationResponse create(ReservationRequest request);

    List<ReservationResponse> findMyReservations();

    List<ReservationResponse> findAll();

    void cancel(Long reservationId);

}
