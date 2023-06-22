package com.locality.review.eventmicroservices.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.locality.review.eventmicroservices.payload.EventDto;
import com.locality.review.eventmicroservices.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "*")
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping("/")
	public ResponseEntity<EventDto> saveEvent(@Valid @RequestBody EventDto eventDto,
			@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
			@RequestParam Long localityId, @RequestParam Long typeId) {

		System.out.println(eventDto);
		return new ResponseEntity<EventDto>(this.eventService.saveEvent(eventDto, token, localityId, typeId),
				HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public ResponseEntity<List<EventDto>> findAllEvents() {

		return new ResponseEntity<List<EventDto>>(this.eventService.getAllEvent(), HttpStatus.OK);
	}

	@GetMapping("/recent")
	public ResponseEntity<List<EventDto>> findRecentEvent() {

		return new ResponseEntity<List<EventDto>>(this.eventService.getRecentEvent(), HttpStatus.OK);
	}

	@GetMapping("/byuser")
	public ResponseEntity<List<EventDto>> findEventByUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {

		return ResponseEntity.ok(this.eventService.getAllEventByUser(token));
	}

	@GetMapping("/bylocality/{localityId}")
	public ResponseEntity<List<EventDto>> findEventByLocality(@PathVariable(name = "localityId") String localityId) {

		return ResponseEntity.ok(this.eventService.getAllEventByLocality(Long.parseLong(localityId)));
	}

	@GetMapping("/bytype/{typeId}")
	public ResponseEntity<List<EventDto>> findEventByType(@PathVariable(name = "typeId") String typeId) {

		return ResponseEntity.ok(this.eventService.getAllEventByType(Long.parseLong(typeId)));
	}


	@GetMapping("/bylocality/{localityId}/bytype/{typeId}")
	public ResponseEntity<List<EventDto>> findEventByLocalityByType(
			@PathVariable(name = "localityId") String localityId, @PathVariable(name = "typeId") String typeId) {

		return ResponseEntity
				.ok(this.eventService.getAllEventByLocalityAndType(Long.parseLong(localityId), Long.parseLong(typeId)));
	}


	@PutMapping("/{eventId}")
	public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto event,
			@PathVariable(name = "eventId") String eventId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
		return new ResponseEntity<EventDto>(this.eventService.updateEvent(event, Long.parseLong(eventId), token),
				HttpStatus.OK);
	}

	@DeleteMapping("/{eventId}")
	public ResponseEntity<?> deleteEvent(@PathVariable(name = "eventId") String eventId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {

		boolean deleteReview = this.eventService.deleteEvent(Long.parseLong(eventId), token);
		if (deleteReview) {
			return ResponseEntity.ok(Map.of("message", "Event deleted successfully"));
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}