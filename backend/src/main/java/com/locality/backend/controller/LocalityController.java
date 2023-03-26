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
import org.springframework.web.bind.annotation.RestController;

import com.locality.backend.entity.Locality;
import com.locality.backend.entity.User;
import com.locality.backend.response.SuccessResponse;
import com.locality.backend.service.LocalityService;

@RestController
@RequestMapping("/locality")
public class LocalityController {
	
	@Autowired
	private LocalityService localityService;

	@PostMapping("/")
	public ResponseEntity<SuccessResponse> saveLocality(@RequestBody Locality locality){
		
		
		return new ResponseEntity<SuccessResponse>(SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.CREATED.value())
				.status(HttpStatus.CREATED)
				.message("Locality created successfully")
				.data(Map.of("locality", localityService.saveLocality(locality)))
				.build(),
				HttpStatus.CREATED);
			
	}
	
	
	@GetMapping("/")
	public ResponseEntity<SuccessResponse> findLocality(@RequestBody Locality locality){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Locality found successfully")
				.data(Map.of("locality", localityService.getLocality(locality)))
				.build());
	}

	
	@GetMapping("/{name}")
	public ResponseEntity<SuccessResponse> findLocalityByName(@PathVariable String name){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found locality successfully with name "+ name)
				.data(Map.of("locality", localityService.getLocalityByName(name)))
				.build());
	}
	
	@GetMapping("/all")
	public ResponseEntity<SuccessResponse> findAllLocality(){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Found all localities successfully")
				.data(Map.of("Users", localityService.getAllLocality()))
				.build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SuccessResponse> updateLocality(@RequestBody Locality locality, 
				@PathVariable String id){
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Locality updated successfully")
				.data(Map.of("locality", localityService.updateLocality(locality, Long.parseLong(id))))
				.build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> deleteLocality(@PathVariable String id){
		
		return ResponseEntity.ok(
				SuccessResponse.builder()
				.timeStamp(LocalDateTime.now())
				.statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK)
				.message("Locality deleted successfully")
				.data(Map.of("deleted", localityService.deleteLocality(Long.parseLong(id))))
				.build());
	}
}
