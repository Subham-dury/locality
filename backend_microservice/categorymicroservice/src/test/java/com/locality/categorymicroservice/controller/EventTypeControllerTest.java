package com.locality.categorymicroservice.controller;

import com.locality.categorymicroservice.payload.EventTypeDto;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.payload.LocalityDto;
import com.locality.categorymicroservice.service.EventTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventTypeControllerTest {

    @Mock
    private EventTypeService eventTypeService;

    @InjectMocks
    private EventTypeController controllerUnderTest;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

    @Test
    public void givenEventTypeObject_whenSaveEventType_returnSavedEventType() {
        //Mock
        EventTypeDto input = EventTypeDto.builder().typeOfEvent("dummy").build();
        EventTypeDto savedType = EventTypeDto.builder().eventTypeId(1L).typeOfEvent("dummy").build();

        //Stabbing
        when(eventTypeService.saveEventType(input, token)).thenReturn(savedType);

        ResponseEntity<EventTypeDto> response = controllerUnderTest.saveEventType(token, input);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    public void whenGetAllEventType_thenReturnAll() {
        //Mock
        EventTypeDto savedType = EventTypeDto.builder().eventTypeId(1L).typeOfEvent("dummy").build();

        //Stubbing
        when(eventTypeService.getAllEventType()).thenReturn(Arrays.asList(savedType));

        //Asserting
        ResponseEntity<List<EventTypeDto>> response = controllerUnderTest.getAllEventType();
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenGetEventTypeAndLocality_thenReturnEventTypeAndLocality() {

        //Mock
        LocalityAndEventTypeDto typeAndLocalityDto = LocalityAndEventTypeDto.builder().localityId(1L).name("dummy").eventTypeId(1L).typeOfEvent("dummy").build();

        //Stubbing
        when(eventTypeService.getEventTypeAndLocalityById(1L, 1L)).thenReturn(typeAndLocalityDto);

        //Asserting
        ResponseEntity<LocalityAndEventTypeDto> response = controllerUnderTest.getEventTypeAndLocality(String.valueOf(1), String.valueOf(1L));
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);


    }

    @Test
    public void whenUpdateEventType_thenReturnUpdatedEventType() {
        //Mock
        EventTypeDto input = EventTypeDto.builder().typeOfEvent("dummy").build();
        EventTypeDto savedType = EventTypeDto.builder().eventTypeId(1L).typeOfEvent("dummy").build();

        //Stubbing
        when(eventTypeService.updateEventType(input, 1L, token)).thenReturn(savedType);

        //Asserting
        ResponseEntity<EventTypeDto> response = controllerUnderTest.updateEventType(token, input, String.valueOf(1L));
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void whenDeleteEventType_thenReturnMessage() {
        //Stubbing
        when(eventTypeService.deleteEventType(1L, token)).thenReturn(true);

        //Asserting
        ResponseEntity<?> response = controllerUnderTest.deleteEventType(token, String.valueOf(1L));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
