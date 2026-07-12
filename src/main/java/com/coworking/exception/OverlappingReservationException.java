package com.coworking.exception;

public class OverlappingReservationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public OverlappingReservationException() {

		super("The selected space is already reserved for the requested time range.");

	}

}
