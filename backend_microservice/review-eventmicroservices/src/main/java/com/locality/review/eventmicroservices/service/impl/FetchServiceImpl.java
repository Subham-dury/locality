package com.locality.review.eventmicroservices.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.UserDto;
import com.locality.review.eventmicroservices.service.FetchService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class FetchServiceImpl implements FetchService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${usermicroservice.api.url}")
	private String userMicroserviceUrl;

	@Value("${categorymicroservice.api.url}")
	private String categoryMicroserviceUrl;

	@Override
	public UserDto getUser(Long userId) throws ResourceNotFoundException{

		
			ResponseEntity<UserDto> response = restTemplate.exchange(userMicroserviceUrl + "user/" + userId, HttpMethod.GET,
					null, UserDto.class);

			if(!response.getStatusCode().is2xxSuccessful()) {
				throw new ResourceNotFoundException("User not found");
			}
			return response.getBody();
		

	}

	@Override
	public LocalityAndEventTypeDto getLocality(Long localityId) throws ResourceNotFoundException {
		ResponseEntity<LocalityAndEventTypeDto> response = restTemplate.exchange(categoryMicroserviceUrl + "locality/" + localityId,
				HttpMethod.GET, null, LocalityAndEventTypeDto.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			log.error("Locality with id " + localityId + " not found");
			throw new ResourceNotFoundException("Locality with id " + localityId + " not found");
		}
		System.out.println(response.getBody());
		return response.getBody();
	}

	@Override
	public LocalityAndEventTypeDto getEventTypeAndLocality(Long eventTypeId, Long localityId)  throws ResourceNotFoundException{
		ResponseEntity<LocalityAndEventTypeDto> response = restTemplate.exchange(categoryMicroserviceUrl + "type/eventType/" +eventTypeId + "/locality/" + localityId
				,HttpMethod.GET, null, LocalityAndEventTypeDto.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			log.error("Locality with id " + localityId + " not found");
			throw new ResourceNotFoundException("Locality with id " + localityId + " not found");
		}

		return response.getBody();
	}

}
