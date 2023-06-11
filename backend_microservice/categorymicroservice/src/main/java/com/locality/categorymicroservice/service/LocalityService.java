package com.locality.categorymicroservice.service;

import java.util.List;

import com.locality.categorymicroservice.entity.Locality;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.payload.LocalityDto;

public interface LocalityService {

	public LocalityDto saveLocality(LocalityDto localityDto, String token);

	public List<LocalityDto> getAllLocality();
	
	public LocalityAndEventTypeDto getLocality(Long localityId);

	public LocalityDto updateLocality(LocalityDto localityDto, Long localityId, String token);

	public Boolean deleteLocality(Long localityId, String token);

	public Locality doesLocalityExist(String localityName);

}
