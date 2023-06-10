package com.locality.backend.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.EventType;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.EventTypeDto;
import com.locality.backend.repository.EventTypeRepository;
import com.locality.backend.service.EventTypeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EventTypeServiceImpl implements EventTypeService {

	@Autowired
	private EventTypeRepository eventTypeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EventTypeDto saveEventType(EventTypeDto eventTypeDto) throws ResourceExistsException {

		if (this.eventTypeRepository.findAll().isEmpty()) {
			this.eventTypeRepository.resetAutoIncrement();
		}

		EventType eventType = this.dtoToEventType(eventTypeDto);

		boolean doesEventTypeExist = this.doesEventTypeExist(eventType.getTypeOfEvent()) != null;

		if (doesEventTypeExist) {
			log.error("Event type already exists");
			throw new ResourceExistsException("Event type already exists");

		}

		log.info("Saving new type of event");
		return this.eventTypeToDto(this.eventTypeRepository.save(eventType));
	}

	@Override
	public EventType getEventTypeById(Long eventTypeId) throws ResourceNotFoundException {

		Optional<EventType> searchedEventType = this.eventTypeRepository.findById(eventTypeId);

		if (searchedEventType.isEmpty()) {
			log.error("Event type not found for id " + eventTypeId);
			throw new ResourceNotFoundException("Event type not found for id " + eventTypeId);
		}

		return searchedEventType.get();
	}

	@Override
	public List<EventTypeDto> getAllEventType() throws ResourceNotFoundException {

		List<EventType> allEventType = this.eventTypeRepository.findAll();

		if (allEventType.isEmpty()) {
			log.error("Event type not found");
			throw new ResourceNotFoundException("Event type not found");
		}

		List<EventTypeDto> allEventTypeDtos = allEventType.stream()
				.map(type -> this.eventTypeToDto(type))
				.collect(Collectors.toList());

		return allEventTypeDtos;
	}

	@Override
	public EventTypeDto updateEventType(EventTypeDto eventType, Long eventTypeId) throws ResourceNotFoundException, IllegalArgumentException {
		
		if(eventType.getTypeOfEvent() == null) {
			throw new IllegalArgumentException("No event type for update");
		}

		Optional<EventType> findEventTypeById = this.eventTypeRepository.findById(eventTypeId);

		if (findEventTypeById.isEmpty()) {
			log.error("Event type not found for id " + eventTypeId);
			throw new ResourceNotFoundException("Event type not found for id " + eventTypeId);
		}
		
		EventType toBeUpdatedType = findEventTypeById.get();

		toBeUpdatedType.setTypeOfEvent(eventType.getTypeOfEvent() != null
				&& eventType.getTypeOfEvent().trim().isEmpty()
				? toBeUpdatedType.getTypeOfEvent() : eventType.getTypeOfEvent());

		return this.eventTypeToDto(this.eventTypeRepository.save(toBeUpdatedType));
	}

	@Override
	public boolean deleteEventType(long eventTypeId) throws ResourceNotFoundException {

		Optional<EventType> findEventTypeById = this.eventTypeRepository.findById(eventTypeId);

		if (findEventTypeById.isEmpty()) {
			log.error("Event type not found");
			throw new ResourceNotFoundException("Event type not found");
		}

		this.eventTypeRepository.deleteById(eventTypeId);
		return true;
	}

	@Override
	public EventType doesEventTypeExist(String eventTypeName) {

		return this.eventTypeRepository.findByTypeOfEvent(eventTypeName);
	}

	@Override
	public EventType dtoToEventType(EventTypeDto eventTypeDto) {
		return this.modelMapper.map(eventTypeDto, EventType.class);
	}

	@Override
	public EventTypeDto eventTypeToDto(EventType eventType) {
		return this.modelMapper.map(eventType, EventTypeDto.class);
	}

}
