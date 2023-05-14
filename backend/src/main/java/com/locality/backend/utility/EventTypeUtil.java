package com.locality.backend.utility;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.backend.entity.EventType;
import com.locality.backend.repository.EventTypeRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/typeutil")
@Transactional
public class EventTypeUtil {
	
	@Autowired
	private EventTypeRepository eventTypeRepository;
	
	@PostMapping("/")
	public ResponseEntity<?> saveEventType() throws RuntimeException{
		
		this.eventTypeRepository.resetAutoIncrement();
		
		List<EventType> listOfTypes = Arrays.asList(
				EventType.buildEventType(1L, "Festival celebration", null),
				EventType.buildEventType(2L, "Medical campaign", null),
				EventType.buildEventType(3L, "Fundraising campaign", null),
				EventType.buildEventType(4L, "Charity campaign", null),
				EventType.buildEventType(5L, "Competion - art,singing,etc", null),
				EventType.buildEventType(6L, "Accidents", null),
				EventType.buildEventType(7L, "Theft", null)
				);
		
		try {
			return new ResponseEntity<>(this.eventTypeRepository.saveAll(listOfTypes), HttpStatus.CREATED);
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		

		
	}

}
