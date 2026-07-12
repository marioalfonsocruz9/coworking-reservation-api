package com.coworking.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.coworking.config.properties.PaymentProperties;
import com.coworking.dto.payment.PaymentValidationRequest;
import com.coworking.dto.payment.PaymentValidationResponse;
import com.coworking.service.PaymentValidationService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentValidationServiceImpl implements PaymentValidationService {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(PaymentValidationServiceImpl.class);

	private final RestClient restClient;
	
	private final PaymentProperties paymentProperties;

	@Override
	@CircuitBreaker(name = "paymentService", fallbackMethod = "fallback")
	public boolean validatePayment(Long reservationId, BigDecimal amount) {

		PaymentValidationResponse response = restClient.post().uri(paymentProperties.baseUrl() + "/api/v1/mock/payment/validate")
				.body(new PaymentValidationRequest(reservationId, amount)).retrieve()
				.body(PaymentValidationResponse.class);

		return response.valid();

	}

	public boolean fallback(Long reservationId, BigDecimal amount, Throwable throwable) {
		
		LOGGER.warn(
	            "Payment validation failed. Reservation {} will remain PENDING. Cause: {}",
	            reservationId,
	            throwable.getMessage());
		
		return false;

	}

}
