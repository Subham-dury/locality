package com.locality.backend.payload;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTypeDto {
	
	private Long eventTypeId;
	
	@NotBlank(message="Type cannot be empty")
	@Length(min = 5, max = 50, message = "Event type must be between 5 to 50 characters.")
	private String typeOfEvent;
	
	
}
