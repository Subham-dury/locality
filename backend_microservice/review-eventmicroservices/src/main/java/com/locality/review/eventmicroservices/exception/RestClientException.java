package com.locality.review.eventmicroservices.exception;

public class RestClientException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RestClientException(String message) {
		super(message);
	}

}
