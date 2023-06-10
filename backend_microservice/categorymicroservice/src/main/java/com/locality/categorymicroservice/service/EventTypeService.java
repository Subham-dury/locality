package com.locality.categorymicroservice.service;

import java.util.List;

import com.locality.categorymicroservice.entity.EventType;
import com.locality.categorymicroservice.payload.EventTypeDto;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;

public interface EventTypeService {

	public EventTypeDto saveEventType(EventTypeDto eventTypeDto, Long userId) ;

	public List<EventTypeDto> getAllEventType();
	
	public LocalityAndEventTypeDto getEventTypeAndLocalityById(Long eventTypeId, Long localityId);

	public EventTypeDto updateEventType(EventTypeDto eventTypeDto, Long eventTypeId, Long userId);

	public Boolean deleteEventType(Long eventTypeId, Long userId);

	public EventType doesEventTypeExist(String eventTypeName);

}
