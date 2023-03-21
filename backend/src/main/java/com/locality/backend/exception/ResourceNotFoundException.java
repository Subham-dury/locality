package com.locality.backend.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1985017859139868441L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}