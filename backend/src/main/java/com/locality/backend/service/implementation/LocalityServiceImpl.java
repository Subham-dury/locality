package com.locality.backend.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Locality;
import com.locality.backend.exception.EntityAlreadyExistException;
import com.locality.backend.exception.EntityNotFoundException;
import com.locality.backend.repository.LocalityRepository;
import com.locality.backend.service.LocalityService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LocalityServiceImpl implements LocalityService {

	@Autowired
	private LocalityRepository localityRepository;

	@Override
	public Locality saveLocality(Locality locality) {

		boolean doesLocalityExist = this.doesLocalityExist(locality.getName()) != null;

		if (doesLocalityExist) {
			log.info("Locality with name of ", locality.getName(), " exists");
			throw new EntityAlreadyExistException("Locality needs to be unique");
		}

		log.info("Saving new locality");
		locality.setImg((int) (Math.random() * 4) + 1);
		return (this.localityRepository.save(locality));

	}

	@Override
	public Locality getLocality(Locality locality) {

		Locality searchedLocality = this.doesLocalityExist(locality.getName());

		if (searchedLocality == null) {
			log.info("Locality not found");
			throw new EntityNotFoundException("Locality not found");
		}

		return searchedLocality;
	}

	@Override
	public Locality getLocalityById(Long id) {

		Optional<Locality> searchedLocality = localityRepository.findById(id);

		if (searchedLocality.isEmpty()) {
			log.info("Locality not found");
			throw new EntityNotFoundException("Locality not found");
		}

		return searchedLocality.get();
	}

	@Override
	public List<Locality> getAllLocality() {

		List<Locality> allLocalities = this.localityRepository.findAll();

		allLocalities.forEach(locality -> System.out.println(locality.getName()));

		if (allLocalities.isEmpty()) {
			log.info("Locality not found");
			throw new EntityNotFoundException("Locality not found");
		}

		return allLocalities;
	}

	@Override
	public Locality updateLocality(Locality locality, Long id) {

		Optional<Locality> doesLocalityExist = this.localityRepository.findById(id);

		if (doesLocalityExist.isEmpty()) {
			log.info("Locality not found");
			throw new EntityNotFoundException("Locality not found");
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

		return updatedLocality;

	}

	@Override
	public boolean deleteLocality(Long id) {

		Optional<Locality> doesLocalityExist = this.localityRepository.findById(id);

		if (doesLocalityExist.isEmpty()) {
			log.info("Locality not found");
			throw new EntityNotFoundException("Locality not found");
		}

		this.localityRepository.deleteById(id);
		return true;
	}

	@Override
	public Locality doesLocalityExist(String name) {
		return this.localityRepository.findByName(name);
	}
	
}
