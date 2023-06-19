package com.locality.review.eventmicroservices.payload;

import java.time.LocalDate;

import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "eventDtoBuilder")
@Builder
public class EventDto {

	private Long eventId;

	private LocalDate postDate;

	@NotNull(message = "Event date cannot be null")
	private LocalDate eventDate;

	private int img;

	@NotBlank(message = "Event details cannot be empty")
	@Length(min = 10, max = 255, message = "Review must be precise.")
	private String content;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)  
	private Long userId;

	private String username;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)  
	private Long localityId;

	private String localityname;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)  
	private Long eventTypeId;

	private String eventType;

}
