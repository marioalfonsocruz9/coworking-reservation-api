package com.coworking.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.dto.payment.PaymentValidationRequest;
import com.coworking.dto.payment.PaymentValidationResponse;

@RestController
@RequestMapping("/api/v1/mock/payment")
public class MockPaymentController {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(MockPaymentController.class);

    @PostMapping("/validate")
    public PaymentValidationResponse validate(
            @RequestBody PaymentValidationRequest request)
            throws InterruptedException {

        Thread.sleep(1500);
        
        if (request.amount().compareTo(BigDecimal.valueOf(100)) > 0) {
        	LOGGER.error("Payment service unavailable");
            throw new RuntimeException("Payment service unavailable");
        } else {
        	LOGGER.error("Payment service available");
        }

        return new PaymentValidationResponse(true);

    }

}
