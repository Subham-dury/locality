package com.locality.categorymicroservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalityAndEventTypeDto {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long eventTypeId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String typeOfEvent;
	private Long localityId;
	private String name;

}
