package com.locality.backend.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Event;
import com.locality.backend.entity.EventType;
import com.locality.backend.entity.Locality;
import com.locality.backend.entity.User;
import com.locality.backend.exception.DataExistsException;
import com.locality.backend.exception.DataNotFoundException;
import com.locality.backend.repository.EventRepository;
import com.locality.backend.service.EventService;
import com.locality.backend.service.EventTypeService;
import com.locality.backend.service.LocalityService;
import com.locality.backend.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserService userService; 

	@Autowired
	private LocalityService localityService;
	
	@Autowired
	private EventTypeService eventTypeService;
	
	@Override
	public Event saveEvent(Event event, Long userId, Long localityId, Long eventTypeId) 
			throws DataExistsException, DataNotFoundException{
		
		if(this.eventRepository.findAll().isEmpty()) {
			this.eventRepository.resetAutoIncrement();
		}
		
		User user = this.userService.getUserById(userId);
		
		if(user == null) {
			log.error("User not found");
			throw new DataNotFoundException("User not found");
		}
		
		Locality locality = this.localityService.getLocalityById(localityId);
		
		if(locality == null) {
			log.error("Locality not found");
			throw new DataNotFoundException("Locality not found");
		}
		
		EventType eventType = this.eventTypeService.getEventTypeById(eventTypeId);
		
		if(eventType == null) {
			log.error("Event type not found");
			throw new DataNotFoundException("Event type not found");
		}
		
		if(!this.eventRepository.findByUserAndLocalityAndEventType(user, locality, eventType)
				.isEmpty()) {
			log.error("Duplicate data");
			throw new DataExistsException("You have already reported an event for the locality and type.");
		}
		
		log.info("Saving new event");
		
		event.setImg((int) (Math.random() * 4) + 1);
		event.setPostDate(LocalDate.now());
		event.setUser(user);
		event.setLocality(locality);
		event.setEventType(eventType);
		return (this.eventRepository.save(event));
		
	}

	@Override
	public List<Event> getAllEvent() {
		
		List<Event> events = this.eventRepository.findAll();
		
		if (events.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		return events;
	}

	@Override
	public List<Event> getRecentEvent() {
		
		List<Event> events = this.eventRepository.findTop10ByOrderByPostDateDesc();
		
		if (events.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		return events;
	}

	@Override
	public List<Event> getAllEventByLocality(Long localityId) {
		
		Locality locality = this.localityService.getLocalityById(localityId);
		
		List<Event> events = this.eventRepository.findByLocality(locality);
		
		events.forEach(event -> System.out.println(event.getContent()));		
		
		if (events.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		return events;
	}

	@Override
	public List<Event> getAllEventByUser(Long userId) {
		
		User user = this.userService.getUserById(userId);
		
		List<Event> events = this.eventRepository.findByUser(user);
		

		if (events.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		return events;
	}
	
	@Override
	public List<Event> getAllEventByType(Long eventTypeId) {
		
		EventType eventType = this.eventTypeService.getEventTypeById(eventTypeId);
		
		List<Event> events = this.eventRepository.findByEventType(eventType);
		

		if (events.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		return events;
		
	}
	

	@Override
	public Event updateEvent(Event event, Long eventId) {
		
		Optional<Event> searchedEvent = this.eventRepository.findById(eventId);
		
		if(searchedEvent.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		Event updatedEvent = searchedEvent.get();
		
		updatedEvent.setEventDate(event.getEventDate() == null ?
				updatedEvent.getEventDate() : event.getEventDate());
		
		updatedEvent.setContent(event.getContent() == null ?
				updatedEvent.getContent() : event.getContent());
		
		return updatedEvent;
	}

	@Override
	public boolean deleteEvent(Long id) {
		
		Optional<Event> doesEventExist = eventRepository.findById(id);

		if (doesEventExist.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}

		eventRepository.deleteById(id);
		return true;
	}

	

}
