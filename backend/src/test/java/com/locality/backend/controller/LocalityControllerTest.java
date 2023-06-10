package com.locality.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.locality.backend.payload.LocalityDto;
import com.locality.backend.service.LocalityService;

@ExtendWith(MockitoExtension.class)
public class LocalityControllerTest {

	@Mock
	private LocalityService localityService;
	
	@InjectMocks
	private LocalityController underTestController;
	
	@Test
	public void saveLocality_shouldReturnLocality() {
		
		LocalityDto localityDto = LocalityDto.builder()
				.name("Behala")
				.state("West Bengal")
				.city("Kolkata")
				.about("Beautiful place")
				.build();
		
		LocalityDto expectedLocalityDto = LocalityDto
				.localityDtoBuilder(1L, "Behala", "Kolkata", "West Bengal", 1, "Beautiful place");
		
		when(localityService.saveLocality(localityDto)).thenReturn(expectedLocalityDto);
		
		LocalityDto actualLocalityDto = underTestController.saveLocality(localityDto).getBody();
		
		assertThat(actualLocalityDto).isEqualTo(expectedLocalityDto);
	}
}
