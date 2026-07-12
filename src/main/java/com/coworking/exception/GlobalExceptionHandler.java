package com.coworking.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.coworking.dto.error.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
	        ResourceNotFoundException ex,
	        HttpServletRequest request) {
		
		ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.NOT_FOUND.value(),
	            HttpStatus.NOT_FOUND.getReasonPhrase(),
	            ex.getMessage(),
	            request.getRequestURI(),
	            null
	    );
		
		return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(response);
	}

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
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(
	        BadCredentialsException ex,
	        HttpServletRequest request) {

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.UNAUTHORIZED.value(),
	            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
	            "Invalid email or password",
	            request.getRequestURI(),
	            null);

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	            .body(response);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(
	        AccessDeniedException ex,
	        HttpServletRequest request) {

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.FORBIDDEN.value(),
	            HttpStatus.FORBIDDEN.getReasonPhrase(),
	            "You are not authorized to access this resource.",
	            request.getRequestURI(),
	            null
	    );

	    return ResponseEntity.status(HttpStatus.FORBIDDEN)
	            .body(response);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
	        HttpMessageNotReadableException ex,
	        HttpServletRequest request) {

	    String message = "Malformed request body.";

	    Throwable cause = ex.getCause();
	    
	    if (cause instanceof InvalidFormatException invalidFormat) {

	        if (!invalidFormat.getPath().isEmpty()) {

	            String field = invalidFormat.getPath().get(0).getFieldName();
	            Object value = invalidFormat.getValue();

	            Class<?> targetType = invalidFormat.getTargetType();

	            if (targetType.isEnum()) {

	                String allowedValues = Arrays.stream(targetType.getEnumConstants())
	                        .map(Object::toString)
	                        .collect(Collectors.joining(", "));

	                message = "Invalid value '%s' for field '%s'. Allowed values: %s."
	                        .formatted(value, field, allowedValues);

	            } else {

	                message = "Invalid value '%s' for field '%s'."
	                        .formatted(value, field);
	            }
	        }
	    }

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            HttpStatus.BAD_REQUEST.getReasonPhrase(),
	            message,
	            request.getRequestURI(),
	            null
	    );

	    return ResponseEntity.badRequest()
	            .body(response);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
	        DataIntegrityViolationException ex,
	        HttpServletRequest request) {

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.CONFLICT.value(),
	            HttpStatus.CONFLICT.getReasonPhrase(),
	            "The operation violates a database integrity constraint.",
	            request.getRequestURI(),
	            null
	    );

	    return ResponseEntity.status(HttpStatus.CONFLICT)
	            .body(response);
	}

}