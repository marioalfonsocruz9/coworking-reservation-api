package com.coworking.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.coworking.dto.error.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(
	        BusinessException ex,
	        HttpServletRequest request) {

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.CONFLICT.value(),
	            HttpStatus.CONFLICT.getReasonPhrase(),
	            ex.getMessage(),
	            request.getRequestURI(),
	            null);

	    return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(
	        MethodArgumentNotValidException ex,
	        HttpServletRequest request) {

	    Map<String, String> details = new HashMap<>();

	    ex.getBindingResult()
	            .getFieldErrors()
	            .forEach(error ->
	                    details.put(error.getField(), error.getDefaultMessage()));

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            HttpStatus.BAD_REQUEST.getReasonPhrase(),
	            "Validation failed",
	            request.getRequestURI(),
	            details
	    );

	    return ResponseEntity.badRequest().body(response);

	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedException(
	        Exception ex,
	        HttpServletRequest request) {

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.INTERNAL_SERVER_ERROR.value(),
	            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
	            "An unexpected error occurred",
	            request.getRequestURI(),
	            null
	    );

	    return ResponseEntity.internalServerError()
	            .body(response);

	}

}