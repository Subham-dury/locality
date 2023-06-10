package com.locality.review.eventmicroservices.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.review.eventmicroservices.entity.Event;
import com.locality.review.eventmicroservices.exception.ResourceExistsException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.payload.EventDto;
import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.UserDto;
import com.locality.review.eventmicroservices.repository.EventRepository;
import com.locality.review.eventmicroservices.service.EventService;
import com.locality.review.eventmicroservices.service.FetchService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private FetchService fetchService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EventDto saveEvent(EventDto EventDto, Long userId, Long localityId, Long eventTypeId)
			throws ResourceExistsException, ResourceNotFoundException {

		UserDto user = this.fetchService.getUser(userId);

		LocalityAndEventTypeDto eventTypeAndLocality = this.fetchService.getEventTypeAndLocality(eventTypeId, localityId);

		if (!this.eventRepository
				.findByUserIdAndLocalityIdAndEventTypeIdOrderByPostDate(userId, localityId, eventTypeId)
				.isEmpty()) {
			log.error("Duplicate data");
			throw new ResourceExistsException("You have already reported an event for the locality and type.");
		}
		
		
		Event event = this.dtoToEvent(EventDto);
		log.info("Saving new event");
		event.setImg((int) (Math.random() * 4) + 1);
		event.setPostDate(LocalDate.now());
		event.setUserId(userId);
		event.setUsername(user.getUsername());
		event.setLocalityId(localityId);
		event.setLocalityname(eventTypeAndLocality.getName());
		event.setEventTypeId(eventTypeId);
		event.setEventType(eventTypeAndLocality.getTypeOfEvent());

		return this.eventToDto(this.eventRepository.save(event));

	}

	@Override
	public List<EventDto> getAllEvent() throws ResourceNotFoundException {

		List<Event> events = this.eventRepository.findAll();

		if (events.isEmpty()) {
			log.info("Events not found");
			throw new ResourceNotFoundException("Events not found");
		}

		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getRecentEvent() throws ResourceNotFoundException {

		List<Event> events = this.eventRepository.findByOrderByPostDate();

		if (events.isEmpty()) {
			log.info("Events not found");
			throw new ResourceNotFoundException("Events not found");
		}

		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByLocality(Long localityId) throws ResourceNotFoundException {


		List<Event> events = this.eventRepository.findByLocalityIdOrderByPostDate(localityId);

		events.forEach(event -> System.out.println(event.getContent()));

		if (events.isEmpty()) {
			log.info("Events not found for locality with id "+localityId);
			throw new ResourceNotFoundException("Events not found for locality with id "+localityId);
		}

		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByUser(Long userId) throws ResourceNotFoundException {

		List<Event> events = this.eventRepository.findByUserIdOrderByPostDate(userId);

		if (events.isEmpty()) {
			log.info("Events not found for user with id "+userId);
			throw new ResourceNotFoundException("Events not found for user with id "+userId);
		}

		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByType(Long eventTypeId) throws ResourceNotFoundException {

		List<Event> events = this.eventRepository.findByEventTypeIdOrderByPostDate(eventTypeId);

		if (events.isEmpty()) {
			log.info("Events not found for event type with id "+eventTypeId);
			throw new ResourceNotFoundException("Events not found for event type with id "+eventTypeId);
		}

		List<EventDto> eventDtos = events.stream().map(event -> this.eventToDto(event)).collect(Collectors.toList());
		return eventDtos;

	}

	@Override
	public EventDto updateEvent(EventDto eventDto, Long eventId)
			throws IllegalArgumentException, ResourceNotFoundException {

		Optional<Event> searchedEvent = this.eventRepository.findById(eventId);
		if (searchedEvent.isEmpty()) {
			log.info("Events not found for id " + eventId);
			throw new ResourceNotFoundException("Events not found for id " + eventId);
		}

		if (eventDto == null) {
			throw new IllegalArgumentException("No event details for update");
		}

		Event updatedEvent = searchedEvent.get();

		updatedEvent
				.setEventDate(eventDto.getEventDate() == null ? updatedEvent.getEventDate() : eventDto.getEventDate());

		updatedEvent.setContent(eventDto.getContent() == null ? updatedEvent.getContent() : eventDto.getContent());

		return this.eventToDto(this.eventRepository.save(updatedEvent));
	}

	@Override
	public boolean deleteEvent(Long eventId) throws ResourceNotFoundException {

		Optional<Event> doesEventExist = eventRepository.findById(eventId);

		if (doesEventExist.isEmpty()) {
			log.info("Events not found for id " + eventId);
			throw new ResourceNotFoundException("Events not found for id " + eventId);
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
				null,
				event.getUsername(),
				null,
				event.getLocalityname(),
				null,
				event.getEventType());
	}

	@Override
	public Event dtoToEvent(EventDto eventDto) {
		return this.modelMapper.map(eventDto, Event.class);
	}
	


}
