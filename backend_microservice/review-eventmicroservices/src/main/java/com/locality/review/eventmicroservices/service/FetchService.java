package com.locality.review.eventmicroservices.service;

import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.UserDto;


public interface FetchService {
	
	public UserDto validateUser(String token);
	
	public LocalityAndEventTypeDto getLocality(Long localityId);
	
	public LocalityAndEventTypeDto getEventTypeAndLocality(Long eventTypeId, Long localityId);
	
}
