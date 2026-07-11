package com.coworking.exception;

public class ResourceNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String resource, Object id) {

        super("%s with id '%s' was not found"
                .formatted(resource, id));

    }

}
