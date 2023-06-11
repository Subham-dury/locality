package com.locality.categorymicroservice.exception;

public class RestClientException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RestClientException(String message) {
		super(message);
	}

}
