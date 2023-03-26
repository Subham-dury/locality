package com.locality.backend.response;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ExceptionResponse {
	
	private LocalDateTime timeStamp;
	private int statusCode;
	private HttpStatus status;
	private String typeOfError;
	private Map<?, ?> data;
}
