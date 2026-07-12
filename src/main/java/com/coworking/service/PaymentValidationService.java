package com.coworking.service;

import java.math.BigDecimal;

public interface PaymentValidationService {

    boolean validatePayment(
            Long reservationId,
            BigDecimal amount);

}
