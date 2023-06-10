package com.locality.categorymicroservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.categorymicroservice.payload.EventTypeDto;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.service.EventTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/type")
public class EventTypeController {

	@Autowired
	private EventTypeService eventTypeService;

	@PostMapping("/")
	public ResponseEntity<EventTypeDto> saveLocality(@RequestHeader(name = "userId") String userId,
			@Valid @RequestBody EventTypeDto eventType) {

		return new ResponseEntity<EventTypeDto>(this.eventTypeService.saveEventType(eventType, Long.parseLong(userId)),
				HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public ResponseEntity<List<EventTypeDto>> getAllEventType() {

		return ResponseEntity.ok(this.eventTypeService.getAllEventType());
	}

	@GetMapping("/eventType/{eventTypeId}/locality/{localityId}")
	public ResponseEntity<LocalityAndEventTypeDto> getEventTypeAndLocalityById(@PathVariable(name = "eventTypeId") String eventTypeId,
			@PathVariable(name = "localityId") String localityId) {
		
		
		return ResponseEntity.ok(this.eventTypeService.getEventTypeAndLocalityById(Long.parseLong(eventTypeId),
				Long.parseLong(localityId)));

	}

	@PutMapping("/{eventTypeId}")
	public ResponseEntity<EventTypeDto> updateLocality(@RequestHeader(name = "userId") String userId,
			@RequestBody EventTypeDto eventType, @PathVariable(name = "eventTypeId") String eventTypeId) {

		return ResponseEntity.ok(
				this.eventTypeService.updateEventType(eventType, Long.parseLong(eventTypeId), Long.parseLong(userId)));
	}

	@DeleteMapping("/{eventTypeId}")
	public ResponseEntity<?> deleteLocality(@RequestHeader(name = "userId") String userId,
			@PathVariable(name = "eventTypeId") String eventTypeId) {

		boolean deleteEventType = this.eventTypeService.deleteEventType(Long.parseLong(eventTypeId),
				Long.parseLong(userId));

		if (deleteEventType)
			return ResponseEntity.ok(Map.of("message", "Event type deleted successfully"));

		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}

}
