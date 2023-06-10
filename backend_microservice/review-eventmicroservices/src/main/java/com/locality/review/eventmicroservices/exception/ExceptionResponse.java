package com.locality.review.eventmicroservices.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(staticName = "errResBuilder")
public class ExceptionResponse {

	private LocalDateTime timeStamp;
	private String message;
	private String path;
}
