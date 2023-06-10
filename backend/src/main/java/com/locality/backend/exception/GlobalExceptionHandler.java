package com.locality.backend.exception;

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
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex,
			 WebRequest webRequest) {
		
		
		try {
			List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());

			return new ResponseEntity<FailureResponse>(FailureResponse.builder()
					.timeStamp(LocalDateTime.now())
					.message(messages.get(0)).path(webRequest.getDescription(false).substring(4))
					.build(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {

			return new ResponseEntity<FailureResponse>(FailureResponse.builder()
					.timeStamp(LocalDateTime.now())
					.message(e.getMessage()).path(webRequest.getDescription(false).substring(4))
					.build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<FailureResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			WebRequest webRequest) {
		
		
		try {
			return new ResponseEntity<FailureResponse>(FailureResponse.builder()
					.timeStamp(LocalDateTime.now())
					.message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()).path(webRequest.getDescription(false).substring(4))
					.build(),
					HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			
			return new ResponseEntity<FailureResponse>(FailureResponse.builder()
					.timeStamp(LocalDateTime.now())
					.message(e.getMessage()).path(webRequest.getDescription(false).substring(4))
					.build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler(ResourceExistsException.class)
	public ResponseEntity<FailureResponse> handleDataExistsException(ResourceExistsException ex,
			WebRequest webRequest){
		
		try {
			
			return new ResponseEntity<FailureResponse>(FailureResponse.builder()
					.timeStamp(LocalDateTime.now())
					.message(ex.getMessage()).path(webRequest.getDescription(false).substring(4))
					.build(),
					HttpStatus.BAD_REQUEST);
					
			
		} catch (Exception e) {
			
			return new ResponseEntity<FailureResponse>(FailureResponse.builder()
					.timeStamp(LocalDateTime.now())
					.message(e.getMessage()).path(webRequest.getDescription(false).substring(4))
					.build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleDataNotFoundException(ResourceNotFoundException ex,
			WebRequest webRequest){
		
		try {
			return new ResponseEntity<FailureResponse>(FailureResponse.builder()
					.timeStamp(LocalDateTime.now())
					.message(ex.getMessage()).path(webRequest.getDescription(false).substring(4))
					.build(),
					HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			
			return new ResponseEntity<FailureResponse>(FailureResponse.builder()
					.timeStamp(LocalDateTime.now())
					.message(e.getMessage()).path(webRequest.getDescription(false).substring(4))
					.build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}