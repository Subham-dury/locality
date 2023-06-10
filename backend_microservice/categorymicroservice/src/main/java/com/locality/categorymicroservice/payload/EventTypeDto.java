package com.locality.categorymicroservice.payload;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "typeBuilder")
@NoArgsConstructor
@Builder
@JsonInclude(content = Include.NON_NULL)
public class EventTypeDto {
	
	private Long eventTypeId;
	
	@NotBlank(message="Type cannot be empty")
	@Length(min = 5, max = 50, message = "Event type must be between 5 to 50 characters.")
	private String typeOfEvent;
	
	
}
