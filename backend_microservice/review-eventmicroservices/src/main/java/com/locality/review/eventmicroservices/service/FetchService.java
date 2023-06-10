package com.locality.review.eventmicroservices.service;

import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.UserDto;


public interface FetchService {
	
	public UserDto getUser(Long userId);
	
	public LocalityAndEventTypeDto getLocality(Long localityId);
	
	public LocalityAndEventTypeDto getEventTypeAndLocality(Long eventTypeId, Long localityId);
	
}
