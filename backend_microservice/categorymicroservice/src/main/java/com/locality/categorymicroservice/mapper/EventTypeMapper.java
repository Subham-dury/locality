package com.locality.categorymicroservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.locality.categorymicroservice.entity.EventType;
import com.locality.categorymicroservice.payload.EventTypeDto;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;

@Component
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
