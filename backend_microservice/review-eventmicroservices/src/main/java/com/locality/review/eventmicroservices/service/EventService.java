package com.locality.review.eventmicroservices.service;

import java.util.List;

import com.locality.review.eventmicroservices.entity.Event;
import com.locality.review.eventmicroservices.payload.EventDto;

public interface EventService {

	public EventDto saveEvent(EventDto EventDto, Long userId, Long localityId, Long eventTypeId);

	public List<EventDto> getAllEvent();

	public List<EventDto> getRecentEvent();

	public List<EventDto> getAllEventByLocality(Long localityId);

	public List<EventDto> getAllEventByUser(Long userId);

	public List<EventDto> getAllEventByType(Long eventTypeId);

	public EventDto updateEvent(EventDto EventDto, Long eventId);

	public boolean deleteEvent(Long eventId);

	public EventDto eventToDto(Event event);

	public Event dtoToEvent(EventDto eventDto);

}
