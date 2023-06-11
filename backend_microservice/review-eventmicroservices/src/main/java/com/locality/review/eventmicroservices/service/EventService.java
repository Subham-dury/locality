package com.locality.review.eventmicroservices.service;

import java.util.List;

import com.locality.review.eventmicroservices.payload.EventDto;

public interface EventService {

	public EventDto saveEvent(EventDto EventDto, String token, Long localityId, Long eventTypeId);

	public List<EventDto> getAllEvent();

	public List<EventDto> getRecentEvent();

	public List<EventDto> getAllEventByLocality(Long localityId);

	public List<EventDto> getAllEventByUser(String token);

	public List<EventDto> getAllEventByType(Long eventTypeId);
	
	public List<EventDto> getAllEventByUserAndLocality(String token, Long localityId);
	
	public List<EventDto> getAllEventByLocalityAndType(Long localityId, Long eventTypeId);
	
	public List<EventDto> getAllEventByUserAndLocalityAndType(String token, Long localityId, Long eventTypeId);

	public EventDto updateEvent(EventDto EventDto, Long eventId, String token);

	public boolean deleteEvent(Long eventId, String token);

}
