package com.locality.backend.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.backend.entity.EventType;
import com.locality.backend.service.EventTypeService;

@RestController
@RequestMapping("/type")
public class EventTypeController {
	
	@Autowired
	private EventTypeService eventTypeService;

	@PostMapping("/")
	public ResponseEntity<EventType> saveLocality(@RequestBody EventType eventType){
		
		return new ResponseEntity<EventType>(this.eventTypeService.saveEventType(eventType),
				HttpStatus.CREATED);
			
	}
	
	@GetMapping("/{eventTypeId}")
	public ResponseEntity<EventType> findLocalityById(@PathVariable String eventTypeId){
		
		return ResponseEntity.ok(
				this.eventTypeService.getEventTypeById(Long.parseLong(eventTypeId)));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<EventType>> findAllLocality(){
		
		return ResponseEntity.ok(
				this.eventTypeService.getAllEventType());
	}
	
	@PutMapping("/{eventTypeId}")
	public ResponseEntity<EventType> updateLocality(@RequestBody EventType eventType, 
				@PathVariable String eventTypeId){
		
		return ResponseEntity.ok(
				this.eventTypeService.updateEventType(eventType, Long.parseLong(eventTypeId)));
	}
	
	@DeleteMapping("/{eventTypeId}")
	public ResponseEntity<?> deleteLocality(@PathVariable String eventTypeId){
		
		boolean deleteEventType = this.eventTypeService.deleteEventType(Long.parseLong(eventTypeId));
		
		if(deleteEventType) return ResponseEntity.ok(Map.of("message", "Event type deleted successfully"));
		
		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
	
}
