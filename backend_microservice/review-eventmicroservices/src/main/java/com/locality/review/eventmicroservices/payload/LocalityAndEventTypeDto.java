package com.locality.review.eventmicroservices.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalityAndEventTypeDto {
	
	private Long eventTypeId;
	private String typeOfEvent;
	private Long localityId;
	private String name;

}
