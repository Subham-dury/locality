package com.locality.review.eventmicroservices.service;

import com.locality.review.eventmicroservices.entity.Event;
import com.locality.review.eventmicroservices.entity.Review;
import com.locality.review.eventmicroservices.exception.NotAuthorizedException;
import com.locality.review.eventmicroservices.exception.ResourceExistsException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.mapper.EventMapper;
import com.locality.review.eventmicroservices.payload.EventDto;
import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.ReviewDto;
import com.locality.review.eventmicroservices.payload.UserDto;
import com.locality.review.eventmicroservices.repository.EventRepository;
import com.locality.review.eventmicroservices.service.impl.EventServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private FetchService fetchService;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventServiceImpl serviceUnderTest;

    private ModelMapper modelMapper;
    Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "postDate");
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJKb2huRG9lIiwicm9sZSI6IkFETUlOIiwiaWF0IjoxNjg3MTc1NzMxLCJleHAiOjE2ODcyNjIxMzF9.Z_i5xVeFaLIMmkxdvxEqwYH-fw3P6zvsbVKZvzAae64";

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    public void givenEventBody_whenSaveEvent_thenReturnSavedEvent() {

        //Mock
        EventDto inputEvent = EventDto.builder().eventDate(LocalDate.parse("2023-01-01"))
                .content("dummy-text").build();

        Event event = modelMapper.map(inputEvent, Event.class);
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        LocalityAndEventTypeDto localityandtype = LocalityAndEventTypeDto.builder()
                .localityId(1L).name("Dummy-place").eventTypeId(2L).typeOfEvent("dummy-type").build();

        Event savedEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        EventDto expectedDto = modelMapper.map(savedEvent, EventDto.class);

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(fetchService.getEventTypeAndLocality(2L, 1L)).thenReturn(localityandtype);
        when(eventRepository.findByUserIdAndLocalityIdAndEventTypeId(user.getUserId(),
                 1L, 2L, sortByDateDesc)).thenReturn(Collections.emptyList());
        when(eventMapper.dtoToEvent(inputEvent)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(savedEvent);
        when(eventMapper.eventToDto(savedEvent)).thenReturn(expectedDto);

        //Asserting
        EventDto actualDto = serviceUnderTest.saveEvent(inputEvent, token, 1L, 2L);
        assertThat(actualDto).isNotNull();
        assertEquals(actualDto, expectedDto);

    }

    @Test
    public void givenInvalidToken_whenSaveEvent_thenThrowNotAuthorizedException() {
        //Mock
        EventDto inputEvent = EventDto.builder().eventDate(LocalDate.parse("2023-01-01"))
                .content("dummy-text").build();
        //Stubbing
        when(fetchService.validateUser(token)).thenThrow(NotAuthorizedException.class);

        //Asserting
        assertThrows(NotAuthorizedException.class,
                () -> serviceUnderTest.saveEvent(inputEvent, token, 1L, 2L));

    }

    @Test
    public void givenInvalidLocalityId_whenSaveEvent_thenThrowResourceNotFoundException(){
        //Mock
        EventDto inputEvent = EventDto.builder().eventDate(LocalDate.parse("2023-01-01"))
                .content("dummy-text").build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(fetchService.getEventTypeAndLocality(1L, 1L))
                .thenThrow(ResourceNotFoundException.class);

        //Asserting
        assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.saveEvent(inputEvent, token, 1L, 1L));
    }

    @Test
    public void givenDuplicateEntry_whenSaveEvent_thenThrowResourceExistException() {
        //Mock
        EventDto inputEvent = EventDto.builder().eventDate(LocalDate.parse("2023-01-01"))
                .content("dummy-text").build();

        Event event = modelMapper.map(inputEvent, Event.class);
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        LocalityAndEventTypeDto localityandtype = LocalityAndEventTypeDto.builder()
                .localityId(1L).name("Dummy-place").eventTypeId(2L).typeOfEvent("dummy-type").build();

        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(fetchService.getEventTypeAndLocality(2L, 1L)).thenReturn(localityandtype);
        when(eventRepository.findByUserIdAndLocalityIdAndEventTypeId(user.getUserId(),
                1L, 2L, sortByDateDesc)).thenReturn(Arrays.asList(existingEvent));

        //Asserting
        assertThrows(ResourceExistsException.class,
                () -> serviceUnderTest.saveEvent(inputEvent, token, 1L, 2L));
    }

    @Test
    public void whenGetAllEvent_thenReturnAllEvents(){

        //Mock
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        //Stubbing
        when(eventRepository.findAllByOrderByPostDate()). thenReturn(Arrays.asList(existingEvent));

        //Asserting
        List<EventDto> allEvent = serviceUnderTest.getAllEvent();
        assertThat(allEvent).isNotNull();
        assertThat(allEvent.size()).isEqualTo(1);
        assertThat(allEvent.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetAllEvent_thenThrowResourceNotFoundException(){
        //Stubbing
        when(eventRepository.findAllByOrderByPostDate()).thenReturn((Collections.emptyList()));
        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getAllEvent());
        String expectedMessage = "Events not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(eventRepository, times(1)).findAllByOrderByPostDate();
    }

    @Test
    public void whenGetRecentEvent_thenReturnRecentEvents(){
        //Mock
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        //Stubbing
        when(eventRepository.findTop10ByOrderByPostDate()). thenReturn(Arrays.asList(existingEvent));

        //Asserting
        List<EventDto> allEvent = serviceUnderTest.getRecentEvent();
        assertThat(allEvent).isNotNull();
        assertThat(allEvent.size()).isEqualTo(1);
        assertThat(allEvent.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetRecentEvent_thenThrowResourceNotFoundException(){
        //Stubbing
        when(eventRepository.findTop10ByOrderByPostDate()).thenReturn((Collections.emptyList()));
        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getRecentEvent());
        String expectedMessage = "Events not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(eventRepository, times(1)).findTop10ByOrderByPostDate();
    }

    @Test
    public void givenLocalityId_whenGetAllEventByLocality_thenReturnEvents() {
        //Mock
        Long localityId = 1L;
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        //Stubbing
        when(eventRepository.findByLocalityId(localityId, sortByDateDesc)).thenReturn(Arrays.asList(existingEvent));

        //Asserting
        List<EventDto> allEventByLocality = serviceUnderTest.getAllEventByLocality(localityId);
        assertThat(allEventByLocality).isNotNull();
        assertThat(allEventByLocality.size()).isEqualTo(1);
        assertThat(allEventByLocality.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetAllEventByLocality_thenThrowResourceNotFoundException() {
        //Mock
        Long localityId = 1L;

        //Stubbing
        when(eventRepository.findByLocalityId(localityId, sortByDateDesc)).thenReturn(Collections.emptyList());
        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getAllEventByLocality(localityId));
        String expectedMessage = "Events not found for selected locality";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(eventRepository, times(1)).findByLocalityId(localityId, sortByDateDesc);

    }

    @Test
    public void givenToken_whenGetAllEventByUser_thenReturnEvents() {
        //Mock
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(eventRepository.findByUserId(user.getUserId(), sortByDateDesc)).thenReturn(Arrays.asList(existingEvent));

        //Asserting
        List<EventDto> allEventByUser = serviceUnderTest.getAllEventByUser(token);
        assertThat(allEventByUser).isNotNull();
        assertThat(allEventByUser.size()).isEqualTo(1);
        assertThat(allEventByUser.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetAllEventByUser_thenThrowResourceNotFoundException() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        when(fetchService.validateUser(token)).thenReturn(user);
        when(eventRepository.findByUserId(user.getUserId(), sortByDateDesc)).thenReturn(Collections.emptyList());

        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getAllEventByUser(token));
        String expectedMessage = "Events not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));

    }

    @Test
    public void givenLocalityId_whenGetAllEventByType_thenReturnEvents() {
        //Mock
        Long typeId = 1L;
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        //Stubbing
        when(eventRepository.findByEventTypeId(typeId, sortByDateDesc)).thenReturn(Arrays.asList(existingEvent));

        //Asserting
        List<EventDto> allEventByLocality = serviceUnderTest.getAllEventByType(typeId);
        assertThat(allEventByLocality).isNotNull();
        assertThat(allEventByLocality.size()).isEqualTo(1);
        assertThat(allEventByLocality.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetAllEventByType_thenThrowResourceNotFoundException() {
        //Mock
        Long typeId = 1L;

        //Stubbing
        when(eventRepository.findByEventTypeId(typeId, sortByDateDesc)).thenReturn(Collections.emptyList());
        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getAllEventByType(typeId));
        String expectedMessage = "Events not found for selected event type";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
    }

    @Test
    public void givenLocalityId_whenGetAllEventByTypeAndLocality_thenReturnEvents() {
        //Mock
        Long typeId = 1L;
        Long localityId = 1L;
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        //Stubbing
        when(eventRepository.findByLocalityIdAndEventTypeId(localityId, typeId, sortByDateDesc)).thenReturn(Arrays.asList(existingEvent));

        //Asserting
        List<EventDto> allEventByLocality = serviceUnderTest.getAllEventByLocalityAndType(localityId,typeId);
        assertThat(allEventByLocality).isNotNull();
        assertThat(allEventByLocality.size()).isEqualTo(1);
        assertThat(allEventByLocality.get(0)).isNull();
    }

    @Test
    public void listNotFound_whenGetAllEventByTypeAndLocality_thenThrowResourceNotFoundException() {
        //Mock
        Long typeId = 1L;
        Long localityId = 1L;
        //Stubbing
        when(eventRepository.findByLocalityIdAndEventTypeId(localityId,typeId, sortByDateDesc)).thenReturn(Collections.emptyList());
        //Asserting
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.getAllEventByLocalityAndType(localityId, typeId));
        String expectedMessage = "Events not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
    }

    @Test
    public void givenReviewBody_whenUpdateReview_thenReturnUpdatedReview() {
        //Mock
        EventDto inputEvent = EventDto.builder().content("dummy-text-2").build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();

        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();
        Event updatedEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.now()).img(1).content("dummy-text-2")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();
        EventDto expectedDto = modelMapper.map(updatedEvent, EventDto.class);

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(updatedEvent)).thenReturn(updatedEvent);
        when(eventMapper.eventToDto(updatedEvent)).thenReturn(expectedDto);

        //Asserting
        EventDto actualEventDto = serviceUnderTest.updateEvent(inputEvent, 1L, token);
        assertEquals(actualEventDto, expectedDto);

    }

    @Test
    public void givenInvalidToken_whenUpdateEvent_thenThrowNotAuthorizedException() {
        //Mock
        EventDto inputEvent = EventDto.builder().eventDate(LocalDate.parse("2023-01-01"))
                .content("dummy-text").build();
        //Stubbing
        when(fetchService.validateUser(token)).thenThrow(NotAuthorizedException.class);

        //Asserting
        assertThrows(NotAuthorizedException.class,
                () -> serviceUnderTest.updateEvent(inputEvent, 1L, token));
    }

    @Test
    public void givenInvalidId_whenUpdateEvent_thenThrowResourceNotFoundException() {
        //Mock
        EventDto inputEvent = EventDto.builder().eventDate(LocalDate.parse("2023-01-01"))
                .content("dummy-text").build();
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();


        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        assertThrows(ResourceNotFoundException.class,
                () -> serviceUnderTest.updateEvent(inputEvent, 1L, token));

    }

    @Test
    public void givenNullBody_whenUpdateEvent_thenThrowIllegalArgumentException() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();
        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));

        assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.updateEvent(null, 1L, token));

    }

    @Test
    public void givenEventId_whenDeleteReview_returnTrue() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(1L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));

        //Asserting
        Boolean actualResponse = serviceUnderTest.deleteEvent(1L, token);
        assertTrue(actualResponse);
    }

    @Test
    public void givenInvalidToken_whenDeleteEvent_thenThrowNOtAuthorizedException() {

        //Stubbing
        when(fetchService.validateUser(token)).thenThrow(NotAuthorizedException.class);

        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.deleteEvent(1L, token));

    }

    @Test
    public void givenInvalidEventId_whenDeleteEvent_thenThrowResourceNotFoundException() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        //Asserting
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.deleteEvent(1L, token));

    }

    @Test
    public void givenInvalidUserDetails_whenDeleteReview_thenThrowNotAuthorizedException() {
        //Mock
        UserDto user = UserDto.builder().userId(1L).username("dummy").role("MEMBER").build();
        Event existingEvent = Event.builder().eventId(1L).eventDate(LocalDate.parse("2023-01-01"))
                .postDate(LocalDate.parse("2023-06-15")).img(1).content("dummy-text")
                .userId(2L).username("dummy").localityId(2L).localityname("Dummy-place")
                .eventTypeId(1L).eventType("dummy-type")
                .build();

        //Stubbing
        when(fetchService.validateUser(token)).thenReturn(user);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));

        //Asserting
        assertThrows(NotAuthorizedException.class, () -> serviceUnderTest.deleteEvent(1L, token));

    }
}
