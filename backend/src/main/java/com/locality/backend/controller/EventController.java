package com.locality.backend.controller;

import java.time.LocalDateTime;
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
import com.locality.backend.entity.Review;
import com.locality.backend.response.SuccessResponse;
import com.locality.backend.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	
	@PostMapping("/")
	public ResponseEntity<SuccessResponse> saveEvent(@RequestBody Event event, @RequestParam Long userId,
				@RequestParam Long localityId){
		
		
		return new ResponseEntity<SuccessResponse>(SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.CREATED.value())
				.status(HttpStatus.CREATED)
				.message("Event created successfully")
				.data(Map.of("event", eventService.saveEvent(event, userId, localityId)))
				.build(),
				HttpStatus.CREATED);
			
	}
	
	@GetMapping("/all")
	public ResponseEntity<SuccessResponse> findAllEvents(){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all events successfully")
				.data(Map.of("events", eventService.getAllEvent()))
				.build());
	}
	
	@GetMapping("/recent")
	public ResponseEntity<SuccessResponse> findRecentEvent(){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all events successfully")
				.data(Map.of("events", eventService.getRecentEvent()))
				.build());
	}
	
	@GetMapping("/byuser/{userId}")
	public ResponseEntity<SuccessResponse> findEventByUserid(@PathVariable String userId){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all events successfully")
				.data(Map.of("events", eventService.getAllEventByUser(Long.parseLong(userId))))
				.build());
	}
	
	@GetMapping("/bylocality/{localityId}")
	public ResponseEntity<SuccessResponse> findReviewByLocalityid(@PathVariable String localityId){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all events successfully")
				.data(Map.of("events", eventService.getAllEventByLocality(Long.parseLong(localityId))))
				.build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SuccessResponse> updateReview(@RequestBody Event event, 
				@PathVariable String id){
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Event updated successfully")
				.data(Map.of("event", eventService.updateEvent(event, Long.parseLong(id))))
				.build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> deleteReview(@PathVariable String id){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Event deleted successfully")
				.data(Map.of("deleted", eventService.deleteEvent(Long.parseLong(id))))
				.build());
	}
	
}
