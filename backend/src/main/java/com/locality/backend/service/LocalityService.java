package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.Locality;

public interface LocalityService {

	public Locality saveLocality(Locality locality);

	public Locality getLocalityById(Long localityId);

	public List<Locality> getAllLocality();

	public Locality updateLocality(Locality locality, Long localityId);

	public boolean deleteLocality(Long localityId);

	public Locality doesLocalityExist(String localityName);
	
}
