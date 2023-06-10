package com.locality.review.eventmicroservices.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest webRequest) {

		List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());

		return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().timeStamp(LocalDateTime.now())
				.message(messages.get(0)).path(webRequest.getDescription(false).substring(4)).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			WebRequest webRequest) {

		return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().timeStamp(LocalDateTime.now())
				.message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
				.path(webRequest.getDescription(false).substring(4)).build(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ResourceExistsException.class)
	public ResponseEntity<ExceptionResponse> handleDataExistsException(ResourceExistsException ex,
			WebRequest webRequest) {

		return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().timeStamp(LocalDateTime.now())
				.message(ex.getMessage()).path(webRequest.getDescription(false).substring(4)).build(),
				HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleDataNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {

		return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().timeStamp(LocalDateTime.now())
				.message(ex.getMessage()).path(webRequest.getDescription(false).substring(4)).build(),
				HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(NotAuthorizedException.class)
	public ResponseEntity<ExceptionResponse> handleUnauthorizedException(NotAuthorizedException ex, WebRequest webRequest) {

		return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().timeStamp(LocalDateTime.now())
				.message(ex.getMessage()).path(webRequest.getDescription(false).substring(4)).build(),
				HttpStatus.UNAUTHORIZED);

	}

}