package com.locality.categorymicroservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.locality.categorymicroservice.exception.NotAuthorizedException;
import com.locality.categorymicroservice.payload.UserDto;
import com.locality.categorymicroservice.service.FetchUserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FetchUserServiceImpl implements FetchUserService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${usermicroservice.api.url}")
	private String userMicroserviceUrl;
	
	@Override
	public Boolean chechIsUserAdmin(String token)throws NotAuthorizedException, RestClientException {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token);
			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

	        ResponseEntity<UserDto> response = restTemplate.exchange(userMicroserviceUrl + "user/getUser",
	        		HttpMethod.GET, requestEntity, UserDto.class);
	        
	        if(!response.getStatusCode().is2xxSuccessful()) {
	        	throw new NotAuthorizedException("Invalid token");
	        }
	        
	        return response.getBody().getRole().equals("ADMIN");
	       
		}
		catch(RestClientException e) {
			throw new RestClientException("Invalid token");
		}
        
        
	}
}
