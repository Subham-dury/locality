package com.locality.backend.payload;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "eventDtoBuilder")
public class EventDto {
	
	private Long eventId;
	
	private LocalDate postDate;
	
	@NotNull(message = "Event date cannot be null")
	private LocalDate eventDate;
	
	private int img;

	@NotBlank(message="Event details cannot be empty")
	@Length(min = 10, max = 255, message = "Review must be precise.")
	private String content;
	
	private String username;
	
	private String localityname;
	
	private String eventType;
	
	
}
