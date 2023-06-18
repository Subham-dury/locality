package com.locality.categorymicroservice.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.locality.categorymicroservice.entity.Locality;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class LocalityRepositoryTests {
	
	@Autowired
	private LocalityRepository localityRepository;
	
	private Locality locality;
	

}
