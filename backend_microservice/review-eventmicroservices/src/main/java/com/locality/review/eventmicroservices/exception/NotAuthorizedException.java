package com.locality.review.eventmicroservices.exception;

public class NotAuthorizedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NotAuthorizedException(String message) {
		super(message);
		
	}
}
