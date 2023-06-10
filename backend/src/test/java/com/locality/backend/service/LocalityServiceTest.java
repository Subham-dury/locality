package com.locality.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.locality.backend.entity.Locality;
import com.locality.backend.payload.LocalityDto;
import com.locality.backend.repository.LocalityRepository;
import com.locality.backend.service.implementation.LocalityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LocalityServiceTest {
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private LocalityRepository localityRepository;
	
	@InjectMocks
	private LocalityServiceImpl underTestService;
	
	@Test
	public void saveLocality_ShouldReturnLocality() {
		
		LocalityDto localityDto = LocalityDto.builder()
				.name("Behala")
				.state("West Bengal")
				.city("Kolkata")
				.about("Beautiful place")
				.build();
	
		Locality expectedLocality = 
				Locality.buildLocality(1L, "Behala", "Kolkata", "West Bengal", 1, "Beautiful place", null, null);
		
		LocalityDto expectedLocalityDto = modelMapper.map(expectedLocality, LocalityDto.class);
				
		Locality locality = modelMapper.map(localityDto, Locality.class);
		
		when(localityRepository.save(locality)).thenReturn(expectedLocality);
		
		LocalityDto actualLocalityDto = underTestService.saveLocality(localityDto);
		
		assertThat(actualLocalityDto).isEqualTo(expectedLocalityDto);
		
		
	}
	
	@Test
	public void getLocalityById_ShouldReturnLocality() {
		
		Optional<Locality> locality = Optional.of(Locality.buildLocality(1L,
				"Behala", "Kolkata", "West Bengal", 1, "Beautiful place", null, null));
		
		when(localityRepository.findById(1L)).thenReturn(locality);
		
		LocalityDto expectedLocalityDto = modelMapper.map(locality, LocalityDto.class);
		
		Locality actualLocalityDto = underTestService.getLocalityById(1L);
		
		assertThat(actualLocalityDto).isEqualTo(expectedLocalityDto);
	}
	
	@Test
	public void getAllLocality_ShouldReturnAllLocality() {
		
		Locality locality = Locality.buildLocality(1L,
				"Behala", "Kolkata", "West Bengal", 1, "Beautiful place", null, null);
		
		List<Locality> localityList = new ArrayList<>();
		localityList.add(locality);
		
		List<LocalityDto> expectedLocalityDtos = localityList.stream()
				.map(local -> modelMapper.map(local, LocalityDto.class)).collect(Collectors.toList());
		
		when(localityRepository.findAll()).thenReturn(localityList);
		
		List<LocalityDto> actualLocalityDtoList = underTestService.getAllLocality();
		
		assertThat(actualLocalityDtoList).isEqualTo(expectedLocalityDtos);
		
	}
	
	
}
