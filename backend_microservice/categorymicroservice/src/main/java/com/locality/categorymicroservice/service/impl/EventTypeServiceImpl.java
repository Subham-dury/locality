package com.locality.categorymicroservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.categorymicroservice.entity.EventType;
import com.locality.categorymicroservice.exception.NotAuthorizedException;
import com.locality.categorymicroservice.exception.ResourceExistsException;
import com.locality.categorymicroservice.exception.ResourceNotFoundException;
import com.locality.categorymicroservice.mapper.EventTypeMapper;
import com.locality.categorymicroservice.payload.EventTypeDto;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.repository.EventTypeRepository;
import com.locality.categorymicroservice.service.EventTypeService;
import com.locality.categorymicroservice.service.FetchUserService;
import com.locality.categorymicroservice.service.LocalityService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EventTypeServiceImpl implements EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private FetchUserService fetchUserService;

    @Autowired
    private LocalityService localityService;

    @Autowired
    private EventTypeMapper eventTypeMapper;


    @Override
    public EventTypeDto saveEventType(EventTypeDto eventTypeDto, String token)
            throws ResourceExistsException, NotAuthorizedException {

        if (!this.fetchUserService.checkIsUserAdmin(token)) {
            throw new NotAuthorizedException("User is not authorised");
        }

        EventType eventType = eventTypeMapper.dtoToEventType(eventTypeDto);

        boolean doesEventTypeExist = this.doesEventTypeExist(eventType.getTypeOfEvent()) != null;

        if (doesEventTypeExist) {
            throw new ResourceExistsException("Event type already exists");
        }

        log.info("Saving new type of event");
        EventType savedEventType = this.eventTypeRepository.save(eventType);
        return eventTypeMapper.eventTypeToDto(savedEventType);
    }

    @Override
    public List<EventTypeDto> getAllEventType() throws ResourceNotFoundException {

        List<EventType> allEventType = this.eventTypeRepository.findAll();

        if (allEventType.isEmpty()) {
            throw new ResourceNotFoundException("Event type not found");
        }

        List<EventTypeDto> allEventTypeDtos = allEventType.stream()
                .map(type -> eventTypeMapper.eventTypeToDto(type))
                .collect(Collectors.toList());

        return allEventTypeDtos;
    }

    @Override
    public LocalityAndEventTypeDto getEventTypeAndLocalityById(Long eventTypeId, Long localityId) {
        Optional<EventType> findById = this.eventTypeRepository.findById(eventTypeId);

        if (findById.isEmpty()) {
            throw new ResourceNotFoundException("Event type not found");
        }

        EventType eventType = findById.get();
        LocalityAndEventTypeDto localityById = this.localityService.getLocality(localityId);

        return eventTypeMapper.localityAndEventTypeToDto(eventType, localityById);

    }

    @Override
    public EventTypeDto updateEventType(EventTypeDto eventType, Long eventTypeId, String token)
            throws ResourceNotFoundException, IllegalArgumentException, NotAuthorizedException {

        if (!this.fetchUserService.checkIsUserAdmin(token)) {
            throw new NotAuthorizedException("User is not authorised");
        }

        if ( eventType == null || eventType.getTypeOfEvent() == null) {
            throw new IllegalArgumentException("No event type for update");
        }

        Optional<EventType> findEventTypeById = this.eventTypeRepository.findById(eventTypeId);

        if (findEventTypeById.isEmpty()) {
            throw new ResourceNotFoundException("Event type not found");
        }

        EventType toBeUpdatedType = findEventTypeById.get();

        toBeUpdatedType.setTypeOfEvent(eventType.getTypeOfEvent() != null
                && eventType.getTypeOfEvent().trim().isEmpty()
                ? toBeUpdatedType.getTypeOfEvent() : eventType.getTypeOfEvent());

        return eventTypeMapper.eventTypeToDto(this.eventTypeRepository.save(toBeUpdatedType));
    }

    @Override
    public Boolean deleteEventType(Long eventTypeId, String token)
            throws ResourceNotFoundException, NotAuthorizedException {

        if (!this.fetchUserService.checkIsUserAdmin(token)) {
            throw new NotAuthorizedException("User is not authorised");
        }

        Optional<EventType> findEventTypeById = this.eventTypeRepository.findById(eventTypeId);

        if (findEventTypeById.isEmpty()) {
            throw new ResourceNotFoundException("Event type not found");
        }

        this.eventTypeRepository.deleteById(eventTypeId);
        return true;
    }

    @Override
    public EventType doesEventTypeExist(String eventTypeName) {

        return this.eventTypeRepository.findByTypeOfEvent(eventTypeName);
    }


}
