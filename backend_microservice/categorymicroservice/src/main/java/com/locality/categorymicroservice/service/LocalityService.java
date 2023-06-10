package com.locality.categorymicroservice.service;

import java.util.List;

import com.locality.categorymicroservice.entity.Locality;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.payload.LocalityDto;

public interface LocalityService {

	public LocalityDto saveLocality(LocalityDto localityDto, Long userId);

	public List<LocalityDto> getAllLocality();
	
	public LocalityAndEventTypeDto getLocalityById(Long localityId);

	public LocalityDto updateLocality(LocalityDto localityDto, Long localityId, Long userId);

	public Boolean deleteLocality(Long localityId, Long userId);

	public Locality doesLocalityExist(String localityName);

}
