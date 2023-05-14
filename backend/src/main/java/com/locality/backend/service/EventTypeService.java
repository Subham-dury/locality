package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.EventType;

public interface EventTypeService {
	
	public EventType saveEventType(EventType eventType);
	
	public EventType getEventTypeById(Long EventTypeId);
	
	public List<EventType> getAllEventType();
	
	public EventType updateEventType(EventType eventType, Long eventTypeId);
	
	public boolean deleteEventType(long eventTypeId);
	
	public EventType doesEventTypeExist(String eventTypeName);
}
