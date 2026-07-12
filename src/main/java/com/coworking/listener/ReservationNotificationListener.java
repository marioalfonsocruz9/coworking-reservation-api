package com.coworking.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.coworking.event.ReservationCreatedEvent;

@Component
public class ReservationNotificationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationNotificationListener.class);

    @Async
    @TransactionalEventListener
    public void onReservationConfirmed(ReservationCreatedEvent event) {

        LOGGER.info("""
                ========================================
                Sending confirmation email
                Reservation Id : {}
                User           : {}
                Email          : {}
                Space          : {}
                ========================================
                """,
                event.reservation().getId(),
                event.reservation().getUser().getFullName(),
                event.reservation().getUser().getEmail(),
                event.reservation().getSpace().getName());

    }

}