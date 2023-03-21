package com.locality.backend.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.locality.backend.response.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex,
			HttpServletRequest request) {
		
		try {
			List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());
			
			return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().
					timeStamp(LocalDateTime.now())
					.data(Map.of("messages", messages))
					.status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value())
					.typeOfError("Invalid arguments").build(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			
			return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().
					timeStamp(LocalDateTime.now())
					.data(Map.of("messages", ex.getMessage())).
					status(HttpStatus.INTERNAL_SERVER_ERROR)
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).
					typeOfError("Internal server error").build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler(EntityAlreadyExistException.class)
	protected ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityAlreadyExistException ex) {
		try {
			return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().
					timeStamp(LocalDateTime.now())
					.data(Map.of("messages", ex.getMessage()))
					.status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value())
					.typeOfError("Invalid arguments").build(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<ExceptionResponse>(ExceptionResponse.builder().
					timeStamp(LocalDateTime.now())
					.data(Map.of("messages", ex.getMessage())).
					status(HttpStatus.INTERNAL_SERVER_ERROR)
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).
					typeOfError("Internal server error").build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
