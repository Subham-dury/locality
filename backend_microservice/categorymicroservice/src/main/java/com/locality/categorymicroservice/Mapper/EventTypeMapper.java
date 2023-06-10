package com.locality.categorymicroservice.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.locality.categorymicroservice.entity.EventType;
import com.locality.categorymicroservice.payload.EventTypeDto;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;

public class EventTypeMapper {

	@Autowired
	private ModelMapper modelMapper;

	public EventType dtoToEventType(EventTypeDto eventTypeDto) {
		return this.modelMapper.map(eventTypeDto, EventType.class);
	}

	public EventTypeDto eventTypeToDto(EventType eventType) {
		return this.modelMapper.map(eventType, EventTypeDto.class);
	}

	public LocalityAndEventTypeDto localityAndEventTypeToDto(EventType type, LocalityAndEventTypeDto locality) {

		return LocalityAndEventTypeDto.builder().eventTypeId(type.getEventTypeId()).typeOfEvent(type.getTypeOfEvent())
				.localityId(locality.getLocalityId()).name(locality.getName()).build();

	}

}
