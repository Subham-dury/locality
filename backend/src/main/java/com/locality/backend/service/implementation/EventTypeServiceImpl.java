package com.locality.backend.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.EventType;
import com.locality.backend.exception.DataExistsException;
import com.locality.backend.exception.DataNotFoundException;
import com.locality.backend.repository.EventTypeRepository;
import com.locality.backend.service.EventTypeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EventTypeServiceImpl implements EventTypeService {
	
	@Autowired
	EventTypeRepository eventTypeRepository;

	@Override
	public EventType saveEventType(EventType eventType) throws DataExistsException{
		
		if(this.eventTypeRepository.findAll().isEmpty()) {
			this.eventTypeRepository.resetAutoIncrement();
		}
		
	boolean doesEventTypeExist = this.doesEventTypeExist(eventType.getTypeOfEvent()) != null;
	
	if(doesEventTypeExist) {
		log.error("Event type already exists");
		throw new DataExistsException("Event type already exists");
		
	}
		
		log.info("Saving new type of event");
		return this.eventTypeRepository.save(eventType);
	}

	@Override
	public EventType getEventTypeById(Long EventTypeId) throws DataNotFoundException{
		
		Optional<EventType> searchedEventType = this.eventTypeRepository.findById(EventTypeId);
		
		if(searchedEventType.isEmpty()) {
			log.error("Event type not found");
			throw new DataNotFoundException("Event type not found");
		}
		
		return searchedEventType.get();
	}

	@Override
	public List<EventType> getAllEventType() throws DataNotFoundException{
		
		List<EventType> findAllEventType = this.eventTypeRepository.findAll();
		
		if(findAllEventType.isEmpty()) {
			log.error("Event type not found");
			throw new DataNotFoundException("Event type not found");
		}
		
		return findAllEventType;
	}

	@Override
	public EventType updateEventType(EventType eventType, Long eventTypeId)
			throws DataNotFoundException{
		
		Optional<EventType> findEventTypeById = this.eventTypeRepository.findById(eventTypeId);
		
		if(findEventTypeById.isEmpty()) {
			log.error("Event type not found");
			throw new DataNotFoundException("Event type not found");
		}
		
		EventType toBeUpdatedType = findEventTypeById.get();
		
		toBeUpdatedType.setTypeOfEvent(eventType.getTypeOfEvent() == null ||
				eventType.getTypeOfEvent().startsWith(" ") || eventType.getTypeOfEvent().endsWith(" ")
				? toBeUpdatedType.getTypeOfEvent() : eventType.getTypeOfEvent());
		
		return this.eventTypeRepository.save(toBeUpdatedType);
	}

	@Override
	public boolean deleteEventType(long eventTypeId) throws DataNotFoundException{
		
		Optional<EventType> findEventTypeById = this.eventTypeRepository.findById(eventTypeId);
		
		if(findEventTypeById.isEmpty()) {
			log.error("Event type not found");
			throw new DataNotFoundException("Event type not found");
		}
		
		this.eventTypeRepository.deleteById(eventTypeId);
		return true;
	}

	@Override
	public EventType doesEventTypeExist(String eventTypeName) {
		
		return this.eventTypeRepository.findByTypeOfEvent(eventTypeName);
	}

}
