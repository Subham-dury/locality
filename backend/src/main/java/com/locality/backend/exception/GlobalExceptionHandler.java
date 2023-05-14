package com.locality.backend.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
		
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex,
			HttpServletRequest request) {
		
		
		try {
			List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(Map.of("error", messages.get(0)),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			
			return new ResponseEntity<>(Map.of("error", ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler(DataExistsException.class)
	public ResponseEntity<?> handleDataExistsException(DataExistsException ex) {
		
		try {
			return new ResponseEntity<>(Map.of("messages", ex.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			
			return new ResponseEntity<>(Map.of("messages", ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<?> handleDataNotFoundException(DataNotFoundException ex){
		
		try {
			return new ResponseEntity<>(Map.of("messages", ex.getMessage()),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			
			return new ResponseEntity<>(Map.of("messages", ex.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
}