package com.locality.review.eventmicroservices.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.locality.review.eventmicroservices.entity.Event;
import com.locality.review.eventmicroservices.payload.EventDto;

@Component
public class EventMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public EventDto eventToDto(Event event) {

		return EventDto.eventDtoBuilder(event.getEventId(),
				event.getPostDate(), 
				event.getEventDate(), 
				event.getImg(),
				event.getContent(), 
				null,
				event.getUsername(),
				null,
				event.getLocalityname(),
				null,
				event.getEventType());
	}

	
	public Event dtoToEvent(EventDto eventDto) {
		return this.modelMapper.map(eventDto, Event.class);
	}
	
}
