package com.locality.backend.exception;


public class DataExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataExistsException(String message) {
		super(message);
	}

}