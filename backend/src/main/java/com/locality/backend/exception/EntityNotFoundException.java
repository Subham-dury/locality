package com.locality.backend.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1985017859139868441L;

	public EntityNotFoundException(String message) {
		super(message);
	}
}
