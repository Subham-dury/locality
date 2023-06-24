package com.locality.review.eventmicroservices.controller;

import com.locality.review.eventmicroservices.exception.NotAuthorizedException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.payload.EventDto;
import com.locality.review.eventmicroservices.payload.ReviewDto;
import com.locality.review.eventmicroservices.service.EventService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController controllerUnderTest;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenEventBody_whenSaveEvent_returnSavedEvent() {
        //Mock
        EventDto input = EventDto.builder().eventDate(LocalDate.parse("2023-01-01")).content("dummy").build();
        EventDto savedEvent = EventDto.builder().eventId(1L).postDate(LocalDate.parse("2023-01-01"))
                .eventDate(LocalDate.parse("2023-01-01")).content("dummy").img(1)
                .userId(1L).username("dummy").eventTypeId(1L).eventType("dummy").localityId(1L).localityname("place")
                .build();

        //Stubbing
        when(eventService.saveEvent(input, token, 1L, 1L)).thenReturn(savedEvent);

        //Asserting
        ResponseEntity<EventDto> response = controllerUnderTest.saveEvent(input, token, 1L, 1L);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void givenInvalidObject_whenSaveEvent_thenFailValidation() {
        //Mock
        EventDto input = EventDto.builder().build();

        //Asserting
        Set<ConstraintViolation<EventDto>> errors = validator.validate(input);
        assertThat(errors).hasSize(2);
    }

    @Test
    public void whenGetAllEvents_thenReturnAllEvents() {
        //Mock
        EventDto savedEvent = EventDto.builder().eventId(1L).postDate(LocalDate.parse("2023-01-01"))
                .eventDate(LocalDate.parse("2023-01-01")).content("dummy").img(1)
                .userId(1L).username("dummy").eventTypeId(1L).eventType("dummy").localityId(1L).localityname("place")
                .build();
        //Stubbing
        when(eventService.getAllEvent()).thenReturn(Arrays.asList(savedEvent));

        //Asserting
        ResponseEntity<List<EventDto>> response = controllerUnderTest.findAllEvents();
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenGetRecentEvents_thenReturnRecentEvents() {
        //Mock
        EventDto savedEvent = EventDto.builder().eventId(1L).postDate(LocalDate.parse("2023-01-01"))
                .eventDate(LocalDate.parse("2023-01-01")).content("dummy").img(1)
                .userId(1L).username("dummy").eventTypeId(1L).eventType("dummy").localityId(1L).localityname("place")
                .build();
        //Stubbing
        when(eventService.getRecentEvent()).thenReturn(Arrays.asList(savedEvent));

        //Asserting
        ResponseEntity<List<EventDto>> response = controllerUnderTest.findRecentEvent();
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenGetAllEventByUser_thenReturnAllEvents() {
        //Mock
        EventDto savedEvent = EventDto.builder().eventId(1L).postDate(LocalDate.parse("2023-01-01"))
                .eventDate(LocalDate.parse("2023-01-01")).content("dummy").img(1)
                .userId(1L).username("dummy").eventTypeId(1L).eventType("dummy").localityId(1L).localityname("place")
                .build();
        //Stubbing
        when(eventService.getAllEventByUser(token)).thenReturn(Arrays.asList(savedEvent));

        //Asserting
        ResponseEntity<List<EventDto>> response = controllerUnderTest.findEventByUser(token);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void givenInvalidToken_whenGetAllEventByUser_thenThrowException(){

        //Stubbing
        when(eventService.getAllEventByUser(token)).thenThrow(NotAuthorizedException.class);

        // Asserting
        assertThrows(NotAuthorizedException.class,
                () -> controllerUnderTest.findEventByUser(token));
    }

    @Test
    public void whenGetAllEventByLocality_thenReturnAllEvents() {
        //Mock
        EventDto savedEvent = EventDto.builder().eventId(1L).postDate(LocalDate.parse("2023-01-01"))
                .eventDate(LocalDate.parse("2023-01-01")).content("dummy").img(1)
                .userId(1L).username("dummy").eventTypeId(1L).eventType("dummy").localityId(1L).localityname("place")
                .build();
        //Stubbing
        when(eventService.getAllEventByLocality(1L)).thenReturn(Arrays.asList(savedEvent));

        //Asserting
        ResponseEntity<List<EventDto>> response = controllerUnderTest.findEventByLocality(String.valueOf(1L));
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void givenInvalidId_whenGetAllEventByLocality_thenThrowException(){
        //Stubbing
        when(eventService.getAllEventByLocality(1L)).thenThrow(ResourceNotFoundException.class);

        // Asserting
        assertThrows(ResourceNotFoundException.class,
                () -> controllerUnderTest.findEventByLocality(String.valueOf(1L)));
    }

    @Test
    public void whenGetAllEventByType_thenReturnAllEvents() {
        //Mock
        EventDto savedEvent = EventDto.builder().eventId(1L).postDate(LocalDate.parse("2023-01-01"))
                .eventDate(LocalDate.parse("2023-01-01")).content("dummy").img(1)
                .userId(1L).username("dummy").eventTypeId(1L).eventType("dummy").localityId(1L).localityname("place")
                .build();
        //Stubbing
        when(eventService.getAllEventByType(1L)).thenReturn(Arrays.asList(savedEvent));

        //Asserting
        ResponseEntity<List<EventDto>> response = controllerUnderTest.findEventByType(String.valueOf(1L));
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void givenInvalidId_whenGetAllEventByType_thenThrowException(){
        //Stubbing
        when(eventService.getAllEventByType(1L)).thenThrow(ResourceNotFoundException.class);

        // Asserting
        assertThrows(ResourceNotFoundException.class,
                () -> controllerUnderTest.findEventByType(String.valueOf(1L)));
    }

    @Test
    public void whenGetAllEventByLocalityAndType_thenReturnAllEvents() {
        //Mock
        EventDto savedEvent = EventDto.builder().eventId(1L).postDate(LocalDate.parse("2023-01-01"))
                .eventDate(LocalDate.parse("2023-01-01")).content("dummy").img(1)
                .userId(1L).username("dummy").eventTypeId(1L).eventType("dummy").localityId(1L).localityname("place")
                .build();
        //Stubbing
        when(eventService.getAllEventByLocalityAndType(1L, 1L)).thenReturn(Arrays.asList(savedEvent));

        //Asserting
        ResponseEntity<List<EventDto>> response = controllerUnderTest.
                findEventByLocalityByType(String.valueOf(1L), String.valueOf(1L));
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void givenInvalidId_whenGetAllEventByLocalityAndType_thenThrowException(){
        //Stubbing
        when(eventService.getAllEventByLocalityAndType(1L, 1L)).thenThrow(ResourceNotFoundException.class);

        // Asserting
        assertThrows(ResourceNotFoundException.class,
                () -> controllerUnderTest.findEventByLocalityByType(String.valueOf(1L), String.valueOf(1L)));
    }


    @Test
    public void whenUpdateEvent_thenReturnUpdatedEvent() {
        //Mock
        EventDto input = EventDto.builder().eventDate(LocalDate.parse("2023-01-01")).content("dummy").build();
        EventDto updatedEvent = EventDto.builder().eventId(1L).postDate(LocalDate.parse("2023-01-01"))
                .eventDate(LocalDate.parse("2023-01-01")).content("dummy").img(1)
                .userId(1L).username("dummy").eventTypeId(1L).eventType("dummy").localityId(1L).localityname("place")
                .build();

        //Stubbing
        when(eventService.updateEvent(input, 1L, token)).thenReturn(updatedEvent);

        //Asserting
        ResponseEntity<EventDto> response = controllerUnderTest.updateEvent(input,String.valueOf(1L), token);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void givenInvalidToken_whenUpdateEvent_thenThrowException(){

        //Stubbing
        when(eventService.updateEvent(null, 1L, token)).thenThrow(NotAuthorizedException.class);

        // Asserting
        assertThrows(NotAuthorizedException.class,
                () -> controllerUnderTest.updateEvent(null, String.valueOf(1L), token));
    }


    @Test
    public void whenDeleteEvent_thenReturnMessage() {
        //Stubbing
        when(eventService.deleteEvent(1L, token)).thenReturn(true);

        //Asserting
        ResponseEntity<?> response = controllerUnderTest.deleteEvent( String.valueOf(1L), token);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

}
