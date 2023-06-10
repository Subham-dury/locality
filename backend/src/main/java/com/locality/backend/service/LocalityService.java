package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.Locality;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.LocalityDto;

public interface LocalityService {

	public LocalityDto saveLocality(LocalityDto localityDto) throws ResourceExistsException;

	public Locality getLocalityById(Long localityId) throws ResourceNotFoundException;

	public List<LocalityDto> getAllLocality() throws ResourceNotFoundException;

	public LocalityDto updateLocality(LocalityDto localityDto, Long localityId)
			throws ResourceNotFoundException, IllegalArgumentException;

	public boolean deleteLocality(Long localityId) throws ResourceNotFoundException;

	public Locality doesLocalityExist(String localityName);

	public LocalityDto localityToDto(Locality locality);

	public Locality dtoToLocality(LocalityDto localityDto);

}
