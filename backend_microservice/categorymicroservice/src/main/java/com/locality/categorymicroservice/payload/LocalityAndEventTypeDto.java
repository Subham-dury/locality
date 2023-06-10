package com.locality.categorymicroservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LocalityAndEventTypeDto {
	
	@JsonInclude(content = Include.NON_NULL)
	private Long eventTypeId;
	
	@JsonInclude(content = Include.NON_NULL)
	private String typeOfEvent;
	private Long localityId;
	private String name;

}
