package com.locality.backend.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Locality;
import com.locality.backend.exception.ResourceExistsException;
import com.locality.backend.exception.ResourceNotFoundException;
import com.locality.backend.payload.LocalityDto;
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
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LocalityDto saveLocality(LocalityDto localityDto) throws ResourceExistsException{
		
		if(this.localityRepository.findAll().isEmpty()) {
			this.localityRepository.resetAutoIncrement();
		}

		boolean doesLocalityExist = this.doesLocalityExist(localityDto.getName()) != null;

		if (doesLocalityExist) {
			log.error("Locality with name of "+localityDto.getName()+" exists");
			throw new ResourceExistsException("Locality with name of "+localityDto.getName()+" exists");
		}

		log.info("Saving new locality");
		localityDto.setImg((int) (Math.random() * 4) + 1);
		Locality savedLocality = this.localityRepository.save(this.dtoToLocality(localityDto));
		return this.localityToDto(savedLocality);

	}

	@Override
	public Locality getLocalityById(Long localityId) throws ResourceNotFoundException{

		Optional<Locality> searchedLocality = localityRepository.findById(localityId);

		if (searchedLocality.isEmpty()) {
			log.error("Locality not found for id "+localityId);
			throw new ResourceNotFoundException("Locality not found for id "+localityId);
		}

		return searchedLocality.get();
	}

	@Override
	public List<LocalityDto> getAllLocality() throws ResourceNotFoundException{

		List<Locality> allLocalities = this.localityRepository.findAll();

		if (allLocalities.isEmpty()) {
			log.error("Locality not found");
			throw new ResourceNotFoundException("Locality not found");
		}
		
		List<LocalityDto> allLocalityDtos = allLocalities.stream()
				.map(locality -> this.localityToDto(locality)).collect(Collectors.toList());

		return allLocalityDtos;
	}

	@Override
	public LocalityDto updateLocality(LocalityDto localityDto, Long localityId)
			throws ResourceNotFoundException, IllegalArgumentException{
		
		if(localityDto == null) {
			throw new IllegalArgumentException("No locality body for update");
		}
		
		Optional<Locality> doesLocalityExist = this.localityRepository.findById(localityId);

		if (doesLocalityExist.isEmpty()) {
			log.error("Locality not found for id "+localityId);
			throw new ResourceNotFoundException("Locality not found for id "+localityId);
		}

		Locality toUpdatedLocality = doesLocalityExist.get();
		
		toUpdatedLocality.setName(localityDto.getName() == null || localityDto.getName().trim().isEmpty()
				? toUpdatedLocality.getName() : localityDto.getName().trim());
		
		toUpdatedLocality.setCity(localityDto.getCity() == null || localityDto.getCity().trim().isEmpty()
				? toUpdatedLocality.getCity() : localityDto.getCity().trim());
		
		toUpdatedLocality.setState(localityDto.getState() == null || localityDto.getState().trim().isEmpty()
				? toUpdatedLocality.getState() : localityDto.getState().trim());
		
		toUpdatedLocality.setAbout(localityDto.getAbout() == null || localityDto.getAbout().trim().isEmpty()
				? toUpdatedLocality.getAbout() : localityDto.getAbout().trim());

		Locality updatedLocality = this.localityRepository.save(toUpdatedLocality);
		return this.localityToDto(updatedLocality);

	}

	@Override
	public boolean deleteLocality(Long localityId) throws ResourceNotFoundException{

		Optional<Locality> doesLocalityExist = this.localityRepository.findById(localityId);

		if (doesLocalityExist.isEmpty()) {
			log.error("Locality not found");
			throw new ResourceNotFoundException("Locality not found");
		}

		this.localityRepository.deleteById(localityId);
		return true;
	}

	@Override
	public Locality doesLocalityExist(String localityName) {
		return this.localityRepository.findByName(localityName);
	}


	@Override
	public LocalityDto localityToDto(Locality locality) {
		return this.modelMapper.map(locality, LocalityDto.class);
	}

	@Override
	public Locality dtoToLocality(LocalityDto localityDto) {
		return this.modelMapper.map(localityDto, Locality.class);
	}
	
}
