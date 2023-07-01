package com.locality.categorymicroservice.service;

import com.locality.categorymicroservice.entity.EventType;
import com.locality.categorymicroservice.exception.NotAuthorizedException;
import com.locality.categorymicroservice.exception.ResourceExistsException;
import com.locality.categorymicroservice.exception.ResourceNotFoundException;
import com.locality.categorymicroservice.mapper.EventTypeMapper;
import com.locality.categorymicroservice.payload.EventTypeDto;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.repository.EventTypeRepository;
import com.locality.categorymicroservice.service.impl.EventTypeServiceImpl;
import jdk.jfr.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventTypeServiceTest {

    private ModelMapper modelMapper;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private EventTypeMapper eventTypeMapper;

    @Mock
    private FetchService fetchService;

    @Mock
    private LocalityService localityService;

    @InjectMocks
    private EventTypeServiceImpl serviceUnderTest;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    public void givenEventTypeObject_whenSaveEventType_thenReturnSavedEventTypeDto() {

        //Mocks
        EventTypeDto inputEventTypeDto = EventTypeDto.builder()
                .typeOfEvent("Dummy-type")
                .build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
        EventType eventType = modelMapper.map(inputEventTypeDto, EventType.class);

        EventType savedEventType = EventType.builder()
                .eventTypeId(1L)
                .typeOfEvent("Dummy-type")
                .build();
        EventTypeDto expectedEventTypeDto = modelMapper.map(savedEventType, EventTypeDto.class);

        EventTypeDto toBeReturnedEventTypeDto = EventTypeDto.builder()
                .eventTypeId(1L)
                .typeOfEvent("Dummy-type").build();

        // Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(true);
        when(eventTypeMapper.dtoToEventType(inputEventTypeDto)).thenReturn(eventType);

        when(eventTypeRepository.findByTypeOfEvent(eventType.getTypeOfEvent())).thenReturn(null);

        when(eventTypeRepository.save(eventType)).thenReturn(savedEventType);
        when(eventTypeMapper.eventTypeToDto(savedEventType)).thenReturn(toBeReturnedEventTypeDto);

        //Asserting
        EventTypeDto actualEventTypeDto = serviceUnderTest.saveEventType(inputEventTypeDto, token);
        assertThat(actualEventTypeDto).isNotNull();
        assertEquals(actualEventTypeDto, expectedEventTypeDto);

        verify(fetchService, times(1)).checkIsUserAdmin(token);
        verify(eventTypeRepository, times(1)).findByTypeOfEvent(eventType.getTypeOfEvent());
        verify(eventTypeRepository, times(1)).save(eventType);

    }

    @Test
    public void givenInvalidToken_whenSaveEventType_throwNotAuthorizedException() {

        //Mocks
        EventTypeDto inputEventTypeDto = EventTypeDto.builder()
                .typeOfEvent("Dummy-type").build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(false);

        //Assertion
        NotAuthorizedException exception = assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.saveEventType(inputEventTypeDto, token));
        String expectedMessage = "User is not authorised";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(fetchService, times(1)).checkIsUserAdmin(token);
    }

    @Test
    public void givenDuplicateEventType_whenSaveEventType_throwResourceExistsException() {

        //Mocks
        EventTypeDto inputEventTypeDto = EventTypeDto.builder()
                .typeOfEvent("Dummy-type").build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
        EventType eventType = modelMapper.map(inputEventTypeDto, EventType.class);
        EventType existingEventType = EventType.builder()
                .eventTypeId(1L).typeOfEvent("Dummy-type").build();

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(true);
        when(eventTypeMapper.dtoToEventType(inputEventTypeDto)).thenReturn(eventType);
        when(eventTypeRepository.findByTypeOfEvent(eventType.getTypeOfEvent())).thenReturn(existingEventType);

        //Assertion
        Exception exception = assertThrows(ResourceExistsException.class, () -> serviceUnderTest.saveEventType(inputEventTypeDto, token));
        String expectedMessage = "Event type already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(eventTypeRepository, times(1)).findByTypeOfEvent(inputEventTypeDto.getTypeOfEvent());

    }

    @Test
    public void whenGetAllEventType_thenReturnAllEventTypes() {

        //Mock
        EventType eventType = EventType.builder().eventTypeId(1l).typeOfEvent("Dummy-type").build();
        List<EventType> listOfEventTypes = Arrays.asList(eventType);

        //Stubbing
        when(eventTypeRepository.findAll()).thenReturn(listOfEventTypes);

        //Asserting
        List<EventTypeDto> allEventType = serviceUnderTest.getAllEventType();
        assertThat(allEventType).isNotNull();
        assertEquals(allEventType.size(), 1);
        assertEquals(allEventType.get(0), null);
        verify(eventTypeRepository, times(1)).findAll();

    }

    @Test
    public void listNotFound_whenGetAllEventType_throwsResourceNotFoundException() {
        //Stubbing
        when(eventTypeRepository.findAll()).thenReturn(Collections.emptyList());

        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.getAllEventType());
        String expectedMessage = "Event type not found";
        String actualMessage = exception.getMessage();
        assertTrue(expectedMessage.equalsIgnoreCase(actualMessage));

    }

    @Test
    public void givenLocalityIdAndEventTypeId_whenGetEventTypeAndLocalityById_thenReturnLocalityAndEventType() {

        //Mock
        EventType eventType = EventType.builder().eventTypeId(1l).typeOfEvent("Dummy-type").build();
        LocalityAndEventTypeDto locality = LocalityAndEventTypeDto.builder()
                .localityId(1L).name("Dummy-place").build();

        LocalityAndEventTypeDto localityAndEventTypeDto = LocalityAndEventTypeDto.builder()
                .localityId(1L).name("Dummy-place").eventTypeId(1L).typeOfEvent("Dummy-type").build();

        //Stubbing
        when(eventTypeRepository.findById(1L)).thenReturn(Optional.of(eventType));
        when(localityService.getLocality(1L)).thenReturn(locality);
        when(eventTypeMapper.localityAndEventTypeToDto(eventType, locality)).thenReturn(localityAndEventTypeDto);

        //Asserting
        LocalityAndEventTypeDto eventTypeAndLocalityById = serviceUnderTest.getEventTypeAndLocalityById(1L, 1L);
        assertThat(eventTypeAndLocalityById).isNotNull();
        assertThat(eventTypeAndLocalityById.getTypeOfEvent()).isEqualTo("Dummy-type");
        assertThat(eventTypeAndLocalityById.getName()).isEqualTo("Dummy-place");
    }

    @Test
    public void givenInvalidEventTypeId_whenGetEventTypeAndLocalityById_throwResourceNotFoundException() {

        //Stubbing
        when(eventTypeRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getEventTypeAndLocalityById(1L, 1L));

        String expectedMessage = "Event type not found";
        String actualMessage = exception.getMessage();
        assertTrue(expectedMessage.equalsIgnoreCase(actualMessage));

    }

    @Test
    public void givenInvalidLocalityId_whenGetEventTypeAndLocalityById_throwResourceNotFoundException() {

        //Mock
        EventType eventType = EventType.builder().eventTypeId(1l).typeOfEvent("Dummy-type").build();

        //Stubbing
        when(eventTypeRepository.findById(1L)).thenReturn(Optional.of(eventType));
        when(localityService.getLocality(1L)).thenThrow(ResourceNotFoundException.class);

        //Asserting
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.getEventTypeAndLocalityById(1L, 1L));
        verify(eventTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void givenEventTypeObject_whenUpdateEventType_thenReturnUpdatedEventType() {

        //Mock
        EventTypeDto inputEventType = EventTypeDto.builder().typeOfEvent("Dummy-type-1").build();
        EventType existingEventType = EventType.builder().eventTypeId(1L).typeOfEvent("Dummy-type").build();
        EventType updatedEventType = EventType.builder().eventTypeId(1L).typeOfEvent("Dummy-type-1").build();
        EventTypeDto expectedEventTypeDto = modelMapper.map(updatedEventType, EventTypeDto.class);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(true);
        when(eventTypeRepository.findById(1L)).thenReturn(Optional.of(existingEventType));
        when(eventTypeRepository.save(updatedEventType)).thenReturn(updatedEventType);
        when(eventTypeMapper.eventTypeToDto(updatedEventType)).thenReturn(expectedEventTypeDto);

        //Assertion
        EventTypeDto actualUpdatedEventTypeDto = serviceUnderTest.updateEventType(inputEventType, 1L, token);
        assertThat(actualUpdatedEventTypeDto).isNotNull();
        assertEquals(actualUpdatedEventTypeDto, expectedEventTypeDto);
    }

    @Test
    public void givenInvalidToken_whenUpdateEventType_thenThrowNotAuthorizedException() {

        //Mock
        EventTypeDto inputEventType = EventTypeDto.builder().typeOfEvent("Dummy-type-1").build();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(false);

        //Asserting
        NotAuthorizedException exception = assertThrows(NotAuthorizedException.class,
                () -> serviceUnderTest.updateEventType(inputEventType, 1L, token));
        String expectedMessage = "User is not authorised";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(fetchService, times(1)).checkIsUserAdmin(token);

    }

    @Test
    public void givenNullBody_whenUpdateEventType_thenThrowIllegalArgumentException(){
        //Mock
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(true);

        //Asserting
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serviceUnderTest.updateEventType(null, 1L, token));
        String expectedMessage ="No event type for update";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
    }

    @Test
    public void givenInvalidId_whenUpdateEventType_thenThrowResourceNotFoundException() {
        //Mock
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
        EventTypeDto inputEventType = EventTypeDto.builder().typeOfEvent("Dummy-type-1").build();

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(true);
        when(eventTypeRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.updateEventType(inputEventType, 1L, token));

    }

    @Test
    public void givenValidId_whenDeleteEventType_thenReturnTrue() {

        //Mock
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";
        EventType exisitngEventType = EventType.builder().eventTypeId(1L).typeOfEvent("Dummy-type-1").build();

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(true);
        when(eventTypeRepository.findById(1L)).thenReturn(Optional.of(exisitngEventType));

        //Asserting
        assertTrue(serviceUnderTest.deleteEventType(1L, token));

    }

    @Test
    public void givenInvalidToken_whenDeleteEventType_thenThrowNotAuthorizedException() {
        //Mock
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(false);

        //Asserting
        NotAuthorizedException exception = assertThrows(NotAuthorizedException.class,
                () -> serviceUnderTest.deleteEventType(1L, token));
        String expectedMessage = "User is not authorised";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(fetchService, times(1)).checkIsUserAdmin(token);
    }

    @Test
    public void givenInvalidId_whenDeleteEventType_thenThrowResourceNotFoundException() {
        //Mock
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcm5hbWUiOiJTdWJoYW0iLCJyb2xlIjoiTUVNQkVSIiwiaWF0IjoxNjg2OTQxNTQ1LCJleHAiOjE2ODcwMjc5NDV9.C76peR_nITusBsIu778jsDRXrWManBx5UQGrhyOVkH0";

        //Stubbing
        when(fetchService.checkIsUserAdmin(token)).thenReturn(true);
        when(eventTypeRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.deleteEventType(1L, token));
        String expectedMessage = "Event type not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(fetchService, times(1)).checkIsUserAdmin(token);
    }


}
