package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.EventType;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.EventTypeDto;

public interface EventTypeService {

	public EventTypeDto saveEventType(EventTypeDto eventTypeDto) throws ResourceExistsException;

	public EventType getEventTypeById(Long eventTypeId) throws ResourceNotFoundException;

	public List<EventTypeDto> getAllEventType() throws ResourceNotFoundException;

	public EventTypeDto updateEventType(EventTypeDto eventTypeDto, Long eventTypeId)
			throws ResourceNotFoundException, IllegalArgumentException;

	public boolean deleteEventType(long eventTypeId) throws ResourceNotFoundException;

	public EventType doesEventTypeExist(String eventTypeName);

	public EventType dtoToEventType(EventTypeDto eventTypeDto);

	public EventTypeDto eventTypeToDto(EventType eventType);
}
