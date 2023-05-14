package com.locality.backend.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Locality;
import com.locality.backend.exception.DataExistsException;
import com.locality.backend.exception.DataNotFoundException;
import com.locality.backend.repository.LocalityRepository;
import com.locality.backend.service.LocalityService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LocalityServiceImpl implements LocalityService {
	
	@Autowired
	LocalityRepository localityRepository;

	@Override
	public Locality saveLocality(Locality locality) throws DataExistsException{
		
		if(this.localityRepository.findAll().isEmpty()) {
			this.localityRepository.resetAutoIncrement();
		}

		boolean doesLocalityExist = this.doesLocalityExist(locality.getName()) != null;

		if (doesLocalityExist) {
			log.error("Locality with name of ", locality.getName(), " exists");
			throw new DataExistsException("Locality needs to be unique");
		}

		log.info("Saving new locality");
		locality.setImg((int) (Math.random() * 4) + 1);
		return (this.localityRepository.save(locality));

	}

	@Override
	public Locality getLocalityById(Long localityId) throws DataNotFoundException{

		Optional<Locality> searchedLocality = localityRepository.findById(localityId);

		if (searchedLocality.isEmpty()) {
			log.error("Locality not found");
			throw new DataNotFoundException("Locality not found");
		}

		return searchedLocality.get();
	}

	@Override
	public List<Locality> getAllLocality() throws DataNotFoundException{

		List<Locality> allLocalities = this.localityRepository.findAll();

		if (allLocalities.isEmpty()) {
			log.error("Locality not found");
			throw new DataNotFoundException("Locality not found");
		}

		return allLocalities;
	}

	@Override
	public Locality updateLocality(Locality locality, Long localityId) throws DataNotFoundException{

		Optional<Locality> doesLocalityExist = this.localityRepository.findById(localityId);

		if (doesLocalityExist.isEmpty()) {
			log.error("Locality not found");
			throw new DataNotFoundException("Locality not found");
		}

		Locality updatedLocality = doesLocalityExist.get();
		
		updatedLocality.setName(locality.getName() == null || locality.getName().startsWith(" ")
				|| locality.getName().endsWith(" ")
				? updatedLocality.getName() : locality.getName());
		
		updatedLocality.setCity(locality.getCity() == null || locality.getCity().startsWith(" ")
				|| locality.getCity().endsWith(" ")
				? updatedLocality.getCity() : locality.getCity());
		
		updatedLocality.setState(locality.getState() == null || locality.getState().startsWith(" ")
				|| locality.getState().endsWith(" ")
				? updatedLocality.getState() : locality.getState());
		
		updatedLocality.setAbout(locality.getAbout() == null || locality.getAbout().startsWith(" ")
				|| locality.getAbout().endsWith(" ")
				? updatedLocality.getAbout() : locality.getAbout());

		return this.localityRepository.save(updatedLocality);

	}

	@Override
	public boolean deleteLocality(Long localityId) throws DataNotFoundException{

		Optional<Locality> doesLocalityExist = this.localityRepository.findById(localityId);

		if (doesLocalityExist.isEmpty()) {
			log.error("Locality not found");
			throw new DataNotFoundException("Locality not found");
		}

		this.localityRepository.deleteById(localityId);
		return true;
	}

	@Override
	public Locality doesLocalityExist(String localityName) {
		return this.localityRepository.findByName(localityName);
	}
	
}
