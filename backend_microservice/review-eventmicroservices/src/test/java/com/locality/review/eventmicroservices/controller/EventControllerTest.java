package com.locality.review.eventmicroservices.controller;

import com.locality.review.eventmicroservices.payload.EventDto;
import com.locality.review.eventmicroservices.payload.ReviewDto;
import com.locality.review.eventmicroservices.service.EventService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController controllerUnderTest;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

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
    public void whenDeleteEvent_thenReturnMessage() {
        //Stubbing
        when(eventService.deleteEvent(1L, token)).thenReturn(true);

        //Asserting
        ResponseEntity<?> response = controllerUnderTest.deleteEvent( String.valueOf(1L), token);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

}
