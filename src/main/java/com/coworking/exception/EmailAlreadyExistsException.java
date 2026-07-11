package com.coworking.exception;

public class EmailAlreadyExistsException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException(String email) {
        super("Email '%s' is already registered".formatted(email));
    }

}
