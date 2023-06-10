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

import com.locality.backend.payload.LocalityDto;
import com.locality.backend.service.LocalityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/locality")
public class LocalityController {
	
	@Autowired
	private LocalityService localityService;

	@PostMapping("/")
	public ResponseEntity<LocalityDto> saveLocality(@Valid @RequestBody LocalityDto localityDto){
		
		return new ResponseEntity<LocalityDto>(this.localityService.saveLocality(localityDto),
				HttpStatus.CREATED);
			
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<LocalityDto>> findAllLocality(){
		
		return ResponseEntity.ok(
				this.localityService.getAllLocality());
	}
	
	@PutMapping("/{localityId}")
	public ResponseEntity<LocalityDto> updateLocality(@RequestBody LocalityDto localityDto, 
				@PathVariable String localityId){
		
		return ResponseEntity.ok(
				this.localityService.updateLocality(localityDto, Long.parseLong(localityId)));
	}
	
	@DeleteMapping("/{localityId}")
	public ResponseEntity<?> deleteLocality(@PathVariable String localityId){
		
		boolean deleteLocality = this.localityService.
				deleteLocality(Long.parseLong(localityId));
		
		if(deleteLocality) return ResponseEntity.ok(Map.of("message", "Locality deleted successfully"));
		
		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
}
