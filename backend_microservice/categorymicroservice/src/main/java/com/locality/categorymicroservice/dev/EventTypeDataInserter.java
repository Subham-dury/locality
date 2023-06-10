package com.locality.categorymicroservice.dev;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.categorymicroservice.entity.EventType;
import com.locality.categorymicroservice.repository.EventTypeRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/typeutil")
@Transactional
public class EventTypeDataInserter {
	
	@Autowired
	private EventTypeRepository eventTypeRepository;
	
	@PostMapping("/")
	public ResponseEntity<?> saveEventType() throws RuntimeException{
		
		this.eventTypeRepository.resetAutoIncrement();
		
		List<EventType> listOfTypes = Arrays.asList(
				EventType.buildEventType(1L, "Festival celebration"),
				EventType.buildEventType(2L, "Medical campaign"),
				EventType.buildEventType(3L, "Fundraising campaign"),
				EventType.buildEventType(4L, "Charity campaign"),
				EventType.buildEventType(5L, "Competion - art,singing,etc"),
				EventType.buildEventType(6L, "Accidents"),
				EventType.buildEventType(7L, "Theft")
				);
		
		try {
			return new ResponseEntity<>(this.eventTypeRepository.saveAll(listOfTypes), HttpStatus.CREATED);
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		

		
	}

}
