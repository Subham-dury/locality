package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.Event;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.EventDto;

public interface EventService {

	public EventDto saveEvent(EventDto EventDto, Long userId, Long localityId, Long eventTypeId)
			throws ResourceExistsException, ResourceNotFoundException;

	public List<EventDto> getAllEvent() throws ResourceNotFoundException;

	public List<EventDto> getRecentEvent() throws ResourceNotFoundException;

	public List<EventDto> getAllEventByLocality(Long localityId) throws ResourceNotFoundException;

	public List<EventDto> getAllEventByUser(Long userId) throws ResourceNotFoundException;

	public List<EventDto> getAllEventByType(Long eventTypeId) throws ResourceNotFoundException;

	public EventDto updateEvent(EventDto EventDto, Long eventId)
			throws IllegalArgumentException, ResourceNotFoundException;

	public boolean deleteEvent(Long eventId) throws ResourceNotFoundException;

	public EventDto eventToDto(Event event);

	public Event dtoToEvent(EventDto eventDto);

}
