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

import com.locality.backend.entity.Event;
import com.locality.backend.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	
	@PostMapping("/")
	public ResponseEntity<Event> saveEvent(@RequestBody Event event, @RequestParam Long userId,
				@RequestParam Long localityId, @RequestParam Long typeId){
		
		
		return new ResponseEntity<Event>(this.eventService.saveEvent(event, userId, localityId, typeId),
				HttpStatus.CREATED);
			
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Event>> findAllEvents(){
		
		return new ResponseEntity<List<Event>>(this.eventService.getAllEvent(), HttpStatus.OK);
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<Event>> findRecentEvent(){
		
		return new ResponseEntity<List<Event>>(this.eventService.getRecentEvent(), HttpStatus.OK);
	}
	
	@GetMapping("/byuser/{userId}")
	public ResponseEntity<List<Event>> findEventByUserid(@PathVariable String userId){
		
		return ResponseEntity.ok(this.eventService.getAllEventByUser(Long.parseLong(userId)));
	}
	
	@GetMapping("/bylocality/{localityId}")
	public ResponseEntity<List<Event>> findReviewByLocalityid(@PathVariable String localityId){
		
		return ResponseEntity.ok(this.eventService.getAllEventByLocality(Long.parseLong(localityId)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Event> updateReview(@RequestBody Event event, 
				@PathVariable String id){
		return new ResponseEntity<Event>(this.eventService.updateEvent(event, Long.parseLong(id)),
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