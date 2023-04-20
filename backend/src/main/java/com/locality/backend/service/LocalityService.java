package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.Locality;

public interface LocalityService {

	public Locality saveLocality(Locality locality);
	
	public Locality getLocality(Locality locality);
	
	public Locality getLocalityById(Long id);
	
	public List<Locality> getAllLocality();
	
	public Locality updateLocality(Locality locality, Long id);
	
	public boolean deleteLocality(Long id);
	
	public Locality doesLocalityExist(String name);
	
}
