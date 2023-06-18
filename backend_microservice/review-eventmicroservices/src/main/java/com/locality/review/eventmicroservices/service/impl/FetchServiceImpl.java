package com.locality.review.eventmicroservices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.locality.review.eventmicroservices.exception.NotAuthorizedException;
import com.locality.review.eventmicroservices.exception.ResourceNotFoundException;
import com.locality.review.eventmicroservices.payload.LocalityAndEventTypeDto;
import com.locality.review.eventmicroservices.payload.UserDto;
import com.locality.review.eventmicroservices.service.FetchService;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
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
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@Override
	public UserDto validateUser(String token) throws NotAuthorizedException, RestClientException {

		try {
			
			ServiceInstance serviceInstance = loadBalancerClient.choose("USER-SERVICE");
			if (serviceInstance == null) {
			    throw new InternalServerErrorException("Failed to connect to server");
			}

			String userServiceUrl = serviceInstance.getUri().toString();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token);
			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

//			ResponseEntity<UserDto> response = restTemplate.exchange(userMicroserviceUrl + "user/authorize",
//					HttpMethod.GET, requestEntity, UserDto.class);
			
			ResponseEntity<UserDto> response = restTemplate.exchange(userServiceUrl + "/user/authorize",
					HttpMethod.GET, requestEntity, UserDto.class);

			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new NotAuthorizedException("Invalid token");
			}

			return response.getBody();

		} catch (RestClientException e) {
			throw new RestClientException("Invalid token");
		}

	}

	@Override
	public LocalityAndEventTypeDto getLocality(Long localityId) throws ResourceNotFoundException {
		try {
			
			ServiceInstance serviceInstance = loadBalancerClient.choose("CATEGORY-SERVICE");
			if (serviceInstance == null) {
			    throw new InternalServerErrorException("Failed to connect to server");
			}			
			
			String categoryServiceUrl = serviceInstance.getUri().toString();

			
//			ResponseEntity<LocalityAndEventTypeDto> response = restTemplate.exchange(
//					categoryMicroserviceUrl + "locality/" + localityId, HttpMethod.GET, null,
//					LocalityAndEventTypeDto.class);
			
			ResponseEntity<LocalityAndEventTypeDto> response = restTemplate.exchange(
					categoryServiceUrl +"/locality/" + localityId, HttpMethod.GET, null,
					LocalityAndEventTypeDto.class);

			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new ResourceNotFoundException("Locality not found");
			}
			System.out.println(response.getBody());
			return response.getBody();
		} catch (RestClientException e) {
			throw new RestClientException("Locality not found");
		}

	}

	@Override
	public LocalityAndEventTypeDto getEventTypeAndLocality(Long eventTypeId, Long localityId)
			throws ResourceNotFoundException {

		try {
			ResponseEntity<LocalityAndEventTypeDto> response = restTemplate.exchange(
					categoryMicroserviceUrl + "type/" + eventTypeId + "/locality/" + localityId, HttpMethod.GET, null,
					LocalityAndEventTypeDto.class);
			
//			ResponseEntity<LocalityAndEventTypeDto> response = restTemplate.exchange(
//					"http://CATEGORY-SERVICE/type/" + eventTypeId + "/locality/" + localityId, HttpMethod.GET, null,
//					LocalityAndEventTypeDto.class);

			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new ResourceNotFoundException("Locality and event type not found");
			}

			return response.getBody();
		} catch (RestClientException e) {
			throw new RestClientException("Locality and event type not found");
		}

	}

}
