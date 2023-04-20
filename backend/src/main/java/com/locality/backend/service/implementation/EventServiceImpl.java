package com.locality.backend.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Event;
import com.locality.backend.entity.Locality;
import com.locality.backend.entity.Review;
import com.locality.backend.entity.User;
import com.locality.backend.exception.EntityNotFoundException;
import com.locality.backend.repository.EventRepository;
import com.locality.backend.service.EventService;
import com.locality.backend.service.LocalityService;
import com.locality.backend.service.UserService;

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
	
	@Override
	public Event saveEvent(Event event, Long userId, Long localityId) {
		
	User user = userService.getUserById(userId);
		
		if(user == null) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}
		
		Locality locality = localityService.getLocalityById(localityId);
		
		if(locality == null) {
			log.info("Locality not found");
			throw new EntityNotFoundException("Locality not found");
		}
		
		log.info("Saving new event");
		event.setImg((int) (Math.random() * 4) + 1);
		event.setPostDate(LocalDate.now());
		event.setUser(user);
		event.setLocality(locality);
		return (this.eventRepository.save(event));
		
	}

	@Override
	public List<Event> getAllEvent() {
		
		List<Event> events = this.eventRepository.findAll();
		
		events.forEach(event -> System.out.println(event.getContent()));
		
		if (events.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		return events;
	}

	@Override
	public List<Event> getRecentEvent() {
		
		List<Event> events = this.eventRepository.findTop10ByOrderByPostDateDesc();
		
		events.forEach(event -> System.out.println(event.getContent()));		

		if (events.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		return events;
	}

	@Override
	public List<Event> getAllEventByLocality(Long localityId) {
		
		Locality locality = localityService.getLocalityById(localityId);
		
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
		
		User user = userService.getUserById(userId);
		
		List<Event> events = this.eventRepository.findByUser(user);
		
		events.forEach(event -> System.out.println(event.getContent()));		
		
		if (events.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		return events;
	}

	@Override
	public Event updateEvent(Event event, Long id) {
		
		Optional<Event> searchedEvent = eventRepository.findById(id);
		
		if(searchedEvent.isEmpty()) {
			log.info("Events not found");
			throw new EntityNotFoundException("Events not found");
		}
		
		Event updatedEvent = searchedEvent.get();
		
		updatedEvent.setEventDate(event.getEventDate() == null ?
				updatedEvent.getEventDate() : event.getEventDate());
		
		updatedEvent.setTypeOfEvent(event.getTypeOfEvent() == null ?
				updatedEvent.getTypeOfEvent() : event.getTypeOfEvent());
		
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
