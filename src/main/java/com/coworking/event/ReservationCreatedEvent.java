package com.coworking.event;

import com.coworking.model.Reservation;

public record ReservationCreatedEvent(
        Reservation reservation) {

}
