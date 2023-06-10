package com.locality.categorymicroservice.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.locality.categorymicroservice.exception.NotAuthorizedException;
import com.locality.categorymicroservice.service.FetchUserService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class FetchUserServiceImpl implements FetchUserService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${usermicroservice.api.url}")
	private String userMicroserviceUrl;
	
	@Override
	public Boolean chechIsUserAdmin(Long userId) {

        ResponseEntity<Map> response = restTemplate.exchange(userMicroserviceUrl + "user/role/" + userId, HttpMethod.GET, null, Map.class);
        
        if(!response.getStatusCode().is2xxSuccessful()) {
        	throw new NotAuthorizedException("User is not an admin");
        }
        
        return (Boolean) response.getBody().get("isAdmin");
        
        
	}

}
