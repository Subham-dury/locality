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

import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.payload.LocalityDto;
import com.locality.categorymicroservice.service.LocalityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/locality")
public class LocalityController {

	@Autowired
	private LocalityService localityService;

	@PostMapping("/")
	public ResponseEntity<LocalityDto> saveLocality(@RequestHeader(name = "userId") String userId,
			@Valid @RequestBody LocalityDto localityDto) {

		return new ResponseEntity<LocalityDto>(this.localityService.saveLocality(localityDto, Long.parseLong(userId)),
				HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public ResponseEntity<List<LocalityDto>> findAllLocality() {

		return ResponseEntity.ok(this.localityService.getAllLocality());
	}
	
	@GetMapping("/{localityId}")
	public ResponseEntity<LocalityAndEventTypeDto> getLocalityById(@PathVariable(name = "localityId") String localityId){
		return ResponseEntity.ok(this.localityService.getLocalityById(Long.parseLong(localityId)));
	}

	@PutMapping("/{localityId}")
	public ResponseEntity<LocalityDto> updateLocality(@RequestHeader(name = "userId") String userId,
			@RequestBody LocalityDto localityDto, @PathVariable String localityId) {

		return ResponseEntity.ok(
				this.localityService.updateLocality(localityDto, Long.parseLong(localityId), Long.parseLong(userId)));
	}

	@DeleteMapping("/{localityId}")
	public ResponseEntity<?> deleteLocality(@RequestHeader(name = "userId") String userId,
			@PathVariable(name = "localityId") String localityId) {
		
		
		boolean deleteLocality = this.localityService.deleteLocality(Long.parseLong(localityId),
				Long.parseLong(userId));

		if (deleteLocality)
			return ResponseEntity.ok(Map.of("message", "Locality deleted successfully"));

		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
}
