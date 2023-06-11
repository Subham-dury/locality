package com.locality.review.eventmicroservices.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.review.eventmicroservices.entity.Event;
import com.locality.review.eventmicroservices.exception.ResourceExistsException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.mapper.EventMapper;
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
	private EventMapper eventMapper;

	@Override
	public EventDto saveEvent(EventDto EventDto, String token, Long localityId, Long eventTypeId)
			throws ResourceExistsException, ResourceNotFoundException {

		UserDto user = this.fetchService.validateUser(token);

		LocalityAndEventTypeDto eventTypeAndLocality = this.fetchService.getEventTypeAndLocality(eventTypeId,
				localityId);

		if (!this.eventRepository
				.findByUserIdAndLocalityIdAndEventTypeIdOrderByPostDate(user.getUserId(), localityId, eventTypeId).isEmpty()) {
			throw new ResourceExistsException("Duplicate data");
		}

		Event event = eventMapper.dtoToEvent(EventDto);
		log.info("Saving new event");
		event.setImg((int) (Math.random() * 4) + 1);
		event.setPostDate(LocalDate.now());
		event.setUserId(user.getUserId());
		event.setUsername(user.getUsername());
		event.setLocalityId(localityId);
		event.setLocalityname(eventTypeAndLocality.getName());
		event.setEventTypeId(eventTypeId);
		event.setEventType(eventTypeAndLocality.getTypeOfEvent());

		return eventMapper.eventToDto(this.eventRepository.save(event));

	}

	@Override
	public List<EventDto> getAllEvent() throws ResourceNotFoundException {

		List<Event> events = this.eventRepository.findAll();

		if (events.isEmpty()) {
			throw new ResourceNotFoundException("Events not found");
		}

		List<EventDto> eventDtos = events.stream().map(event -> eventMapper.eventToDto(event))
				.collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getRecentEvent() throws ResourceNotFoundException {

		List<Event> events = this.eventRepository.findByOrderByPostDate();

		if (events.isEmpty()) {

			throw new ResourceNotFoundException("Events not found");
		}

		List<EventDto> eventDtos = events.stream().map(event -> eventMapper.eventToDto(event))
				.collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByLocality(Long localityId) throws ResourceNotFoundException {

		List<Event> events = this.eventRepository.findByLocalityIdOrderByPostDate(localityId);

		events.forEach(event -> System.out.println(event.getContent()));

		if (events.isEmpty()) {
			throw new ResourceNotFoundException("Events not found for selected locality");
		}

		List<EventDto> eventDtos = events.stream().map(event -> eventMapper.eventToDto(event))
				.collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByUser(String token) throws ResourceNotFoundException {
		
		UserDto user = this.fetchService.validateUser(token);

		List<Event> events = this.eventRepository.findByUserIdOrderByPostDate(user.getUserId());

		if (events.isEmpty()) {
			throw new ResourceNotFoundException("Events not found");
		}

		List<EventDto> eventDtos = events.stream().map(event -> eventMapper.eventToDto(event))
				.collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByType(Long eventTypeId) throws ResourceNotFoundException {

		List<Event> events = this.eventRepository.findByEventTypeIdOrderByPostDate(eventTypeId);

		if (events.isEmpty()) {
			throw new ResourceNotFoundException("Events not found for selected event type");
		}

		List<EventDto> eventDtos = events.stream().map(event -> eventMapper.eventToDto(event))
				.collect(Collectors.toList());
		return eventDtos;

	}

	@Override
	public List<EventDto> getAllEventByUserAndLocality(String token, Long localityId) {
		
		UserDto user = this.fetchService.validateUser(token);
		List<Event> events = this.eventRepository
				.findByUserIdAndLocalityIdOrderByPostDate(user.getUserId(), localityId);
		
		if (events.isEmpty()) {
			throw new ResourceNotFoundException("Events not found");
		}

		List<EventDto> eventDtos = events.stream().map(event -> eventMapper.eventToDto(event))
				.collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByLocalityAndType(Long localityId, Long eventTypeId) {
		List<Event> events = this.eventRepository
				.findByLocalityIdAndEventTypeIdOrderByPostDate(localityId, eventTypeId);
		
		if (events.isEmpty()) {
			throw new ResourceNotFoundException("Events not found");
		}

		List<EventDto> eventDtos = events.stream().map(event -> eventMapper.eventToDto(event))
				.collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public List<EventDto> getAllEventByUserAndLocalityAndType(String token, Long localityId, Long eventTypeId) {
		
		UserDto user = this.fetchService.validateUser(token);
		
		List<Event> events = this.eventRepository
				.findByUserIdAndLocalityIdAndEventTypeIdOrderByPostDate(user.getUserId(), localityId, eventTypeId);
		
		if (events.isEmpty()) {
			throw new ResourceNotFoundException("Events not found");
		}

		List<EventDto> eventDtos = events.stream().map(event -> eventMapper.eventToDto(event))
				.collect(Collectors.toList());
		return eventDtos;
	}

	@Override
	public EventDto updateEvent(EventDto eventDto, Long eventId, String token)
			throws IllegalArgumentException, ResourceNotFoundException {
		
		this.fetchService.validateUser(token);

		Optional<Event> searchedEvent = this.eventRepository.findById(eventId);
		if (searchedEvent.isEmpty()) {
			throw new ResourceNotFoundException("Events not found");
		}

		if (eventDto == null) {
			throw new IllegalArgumentException("No event details for update");
		}

		Event updatedEvent = searchedEvent.get();

		updatedEvent
				.setEventDate(eventDto.getEventDate() == null ? updatedEvent.getEventDate() : eventDto.getEventDate());

		updatedEvent.setContent(eventDto.getContent() == null ? updatedEvent.getContent() : eventDto.getContent());

		return eventMapper.eventToDto(this.eventRepository.save(updatedEvent));
	}

	@Override
	public boolean deleteEvent(Long eventId, String token) throws ResourceNotFoundException {
		
		this.fetchService.validateUser(token);

		Optional<Event> doesEventExist = eventRepository.findById(eventId);

		if (doesEventExist.isEmpty()) {
			throw new ResourceNotFoundException("Events not found");
		}

		eventRepository.deleteById(eventId);
		return true;
	}

}
