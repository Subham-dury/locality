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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.locality.backend.payload.EventDto;
import com.locality.backend.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	
	@PostMapping("/")
	public ResponseEntity<EventDto> saveEvent(@Valid @RequestBody EventDto eventDto, @RequestParam Long userId,
				@RequestParam Long localityId, @RequestParam Long typeId){
		
		System.out.println(eventDto);
		return new ResponseEntity<EventDto>(this.eventService.saveEvent(eventDto, userId, localityId, typeId),
				HttpStatus.CREATED);
			
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<EventDto>> findAllEvents(){
		
		return new ResponseEntity<List<EventDto>>(this.eventService.getAllEvent(), HttpStatus.OK);
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<EventDto>> findRecentEvent(){
		
		return new ResponseEntity<List<EventDto>>(this.eventService.getRecentEvent(), HttpStatus.OK);
	}
	
	@GetMapping("/byuser/{userId}")
	public ResponseEntity<List<EventDto>> findEventByUserid(@PathVariable String userId){
		
		return ResponseEntity.ok(this.eventService.getAllEventByUser(Long.parseLong(userId)));
	}
	
	@GetMapping("/bylocality/{localityId}")
	public ResponseEntity<List<EventDto>> findReviewByLocalityid(@PathVariable String localityId){
		
		return ResponseEntity.ok(this.eventService.getAllEventByLocality(Long.parseLong(localityId)));
	}
	
	@GetMapping("/bytype/{typeId}")
	public ResponseEntity<List<EventDto>> findReviewByTypeid(@PathVariable String typeId){
		
		return ResponseEntity.ok(this.eventService.getAllEventByType(Long.parseLong(typeId)));
	}
	
	@PutMapping("/{eventId}")
	public ResponseEntity<EventDto> updateReview(@RequestBody EventDto event, 
				@PathVariable String eventId){
		return new ResponseEntity<EventDto>(this.eventService.updateEvent(event, Long.parseLong(eventId)),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable String id){
		
		boolean deleteReview = this.eventService.deleteEvent(Long.parseLong(id));
		if(deleteReview) {
			return ResponseEntity.ok(Map.of("message", "Event deleted successfully"));
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}