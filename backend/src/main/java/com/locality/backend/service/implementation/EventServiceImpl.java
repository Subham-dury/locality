package com.locality.backend.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Event;
import com.locality.backend.entity.EventType;
import com.locality.backend.entity.Locality;
import com.locality.backend.entity.User;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.EventDto;
import com.locality.backend.repository.EventRepository;
import com.locality.backend.service.EventService;
import com.locality.backend.service.EventTypeService;
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
	
	@Autowired
	private EventTypeService eventTypeService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public EventDto saveEvent(EventDto EventDto, Long userId, Long localityId, Long eventTypeId) 
			throws ResourceExistsException, ResourceNotFoundException{
		
		if(this.eventRepository.findAll().isEmpty()) {
			this.eventRepository.resetAutoIncrement();
		}
		
		User user = this.userService.getUserById(userId);
		
		if(user == null) {
			log.error("User not found for id "+userId);
			throw new ResourceNotFoundException("User not found for id "+userId);
		}
		
		Locality locality = this.localityService.getLocalityById(localityId);
		
		if(locality == null) {
			log.error("Locality not found for id "+localityId);
			throw new ResourceNotFoundException("Locality not found for id "+localityId);
		}
		
		EventType eventType = this.eventTypeService.getEventTypeById(eventTypeId);
		
		if(eventType == null) {
			log.error("Event type not found for id "+eventTypeId);
			throw new ResourceNotFoundException("Event type not found for id "+eventTypeId);
		}
		
		if(!this.eventRepository.findByUserAndLocalityAndEventType(user, locality, eventType)
				.isEmpty()) {
			log.error("Duplicate data");
			throw new ResourceExistsException("You have already reported an event for the locality and type.");
		}
		
		
		
		Event event = this.dtoToEvent(EventDto);
		log.info("Saving new event");
		event.setImg((int) (Math.random() * 4) + 1);
		event.setPostDate(LocalDate.now());
		event.setUser(user);
		event.setLocality(locality);
		event.setEventType(eventType);
		
		return this.eventToDto(this.eventRepository.save(event));
		
	}

	@Override
	public List<EventDto> getAllEvent() throws ResourceNotFoundException{
		
		List<Event> events = this.eventRepository.findAll();
		
		if (events.isEmpty()) {
			log.info("Events not found");
			throw new ResourceNotFoundException("Events not found");
		}
		
		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getRecentEvent() throws ResourceNotFoundException{
		
		List<Event> events = this.eventRepository.findTop10ByOrderByPostDateDesc();
		
		if (events.isEmpty()) {
			log.info("Events not found");
			throw new ResourceNotFoundException("Events not found");
		}
		
		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByLocality(Long localityId) throws ResourceNotFoundException{
		
		Locality locality = this.localityService.getLocalityById(localityId);
		
		List<Event> events = this.eventRepository.findByLocality(locality);
		
		events.forEach(event -> System.out.println(event.getContent()));		
		
		if (events.isEmpty()) {
			log.info("Events not found");
			throw new ResourceNotFoundException("Events not found");
		}
		
		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByUser(Long userId) throws ResourceNotFoundException{
		
		User user = this.userService.getUserById(userId);
		
		List<Event> events = this.eventRepository.findByUser(user);
		

		if (events.isEmpty()) {
			log.info("Events not found");
			throw new ResourceNotFoundException("Events not found");
		}
		
		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
	}
	
	@Override
	public List<EventDto> getAllEventByType(Long eventTypeId) throws ResourceNotFoundException{
		
		EventType eventType = this.eventTypeService.getEventTypeById(eventTypeId);
		
		List<Event> events = this.eventRepository.findByEventType(eventType);
		

		if (events.isEmpty()) {
			log.info("Events not found");
			throw new ResourceNotFoundException("Events not found");
		}
		
		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
		
	}
	

	@Override
	public EventDto updateEvent(EventDto eventDto, Long eventId) throws IllegalArgumentException, ResourceNotFoundException{
		
		Optional<Event> searchedEvent = this.eventRepository.findById(eventId);
		
		if(eventDto == null) {
			throw new IllegalArgumentException("No event details for update");
		}
		
		if(searchedEvent.isEmpty()) {
			log.info("Events not found for id "+eventId);
			throw new ResourceNotFoundException("Events not found for id "+eventId);
		}
		
		Event updatedEvent = searchedEvent.get();
		
		updatedEvent.setEventDate(eventDto.getEventDate() == null ?
				updatedEvent.getEventDate() : eventDto.getEventDate());
		
		updatedEvent.setContent(eventDto.getContent() == null ?
				updatedEvent.getContent() : eventDto.getContent());
		
		return this.eventToDto(this.eventRepository.save(updatedEvent));
	}

	@Override
	public boolean deleteEvent(Long eventId) throws ResourceNotFoundException{
		
		Optional<Event> doesEventExist = eventRepository.findById(eventId);

		if (doesEventExist.isEmpty()) {
			log.info("Events not found for id "+eventId);
			throw new ResourceNotFoundException("Events not found for id "+eventId);
		}

		eventRepository.deleteById(eventId);
		return true;
	}


	@Override
	public EventDto eventToDto(Event event) {
		
		return EventDto.eventDtoBuilder(event.getEventId(),
				event.getPostDate(),
				event.getEventDate(),
				event.getImg(),
				event.getContent(),
				event.getUser().getUsername(),
				event.getLocality().getName(),
				event.getEventType().getTypeOfEvent());
	}

	@Override
	public Event dtoToEvent(EventDto eventDto) {
		return this.modelMapper.map(eventDto, Event.class);
	}

	

}
