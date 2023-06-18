package com.locality.categorymicroservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.locality.categorymicroservice.entity.Locality;
import com.locality.categorymicroservice.repository.LocalityRepository;
import com.locality.categorymicroservice.service.impl.LocalityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LocalityServiceTests {
	
	@Mock
	private LocalityRepository localityRepository;
	
	@InjectMocks
	private LocalityServiceImpl service_test;
	
	private Locality locality;
	



}
