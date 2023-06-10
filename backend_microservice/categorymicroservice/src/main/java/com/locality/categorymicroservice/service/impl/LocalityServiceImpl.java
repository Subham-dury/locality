package com.locality.categorymicroservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.categorymicroservice.Mapper.LocalityMapper;
import com.locality.categorymicroservice.entity.Locality;
import com.locality.categorymicroservice.exception.NotAuthorizedException;
import com.locality.categorymicroservice.exception.ResourceExistsException;
import com.locality.categorymicroservice.exception.ResourceNotFoundException;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.payload.LocalityDto;
import com.locality.categorymicroservice.repository.LocalityRepository;
import com.locality.categorymicroservice.service.FetchUserService;
import com.locality.categorymicroservice.service.LocalityService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LocalityServiceImpl implements LocalityService {

	@Autowired
	private LocalityRepository localityRepository;

	@Autowired
	private FetchUserService fetchUserService;

	@Autowired
	private LocalityMapper localityMapper;

	@Override
	public LocalityDto saveLocality(LocalityDto localityDto, Long userId)
			throws ResourceExistsException, NotAuthorizedException {

		if (!this.fetchUserService.chechIsUserAdmin(userId)) {
			throw new NotAuthorizedException("User is not authorised");
		}

		boolean doesLocalityExist = this.doesLocalityExist(localityDto.getName()) != null;

		if (doesLocalityExist) {

			throw new ResourceExistsException("Locality with name of " + localityDto.getName() + " exists");
		}

		log.info("Saving new locality");
		localityDto.setImg((int) (Math.random() * 4) + 1);
		Locality savedLocality = this.localityRepository.save(localityMapper.dtoToLocality(localityDto));
		return localityMapper.localityToDto(savedLocality);

	}

	@Override
	public List<LocalityDto> getAllLocality() throws ResourceNotFoundException {

		List<Locality> allLocalities = this.localityRepository.findAll();

		if (allLocalities.isEmpty()) {

			throw new ResourceNotFoundException("Locality not found");
		}

		List<LocalityDto> allLocalityDtos = allLocalities.stream()
				.map(locality -> localityMapper.localityToDto(locality)).collect(Collectors.toList());

		return allLocalityDtos;
	}

	@Override
	public LocalityAndEventTypeDto getLocalityById(Long localityId) {
		Optional<Locality> findById = this.localityRepository.findById(localityId);

		if (findById.isEmpty()) {

			throw new ResourceNotFoundException("Locality not found for id " + localityId);
		}

		return localityMapper.localityToDtoUsingIdAndName(findById.get());

	}

	@Override
	public LocalityDto updateLocality(LocalityDto localityDto, Long localityId, Long userId)
			throws ResourceNotFoundException, IllegalArgumentException, NotAuthorizedException {

		if (!this.fetchUserService.chechIsUserAdmin(userId)) {
			throw new NotAuthorizedException("User is not authorised");
		}

		if (localityDto == null) {
			throw new IllegalArgumentException("No locality body for update");
		}

		Optional<Locality> doesLocalityExist = this.localityRepository.findById(localityId);

		if (doesLocalityExist.isEmpty()) {

			throw new ResourceNotFoundException("Locality not found for id " + localityId);
		}

		Locality toUpdatedLocality = doesLocalityExist.get();

		toUpdatedLocality.setName(
				localityDto.getName() == null || localityDto.getName().trim().isEmpty() ? toUpdatedLocality.getName()
						: localityDto.getName().trim());

		toUpdatedLocality.setCity(
				localityDto.getCity() == null || localityDto.getCity().trim().isEmpty() ? toUpdatedLocality.getCity()
						: localityDto.getCity().trim());

		toUpdatedLocality.setState(
				localityDto.getState() == null || localityDto.getState().trim().isEmpty() ? toUpdatedLocality.getState()
						: localityDto.getState().trim());

		toUpdatedLocality.setAbout(
				localityDto.getAbout() == null || localityDto.getAbout().trim().isEmpty() ? toUpdatedLocality.getAbout()
						: localityDto.getAbout().trim());

		Locality updatedLocality = this.localityRepository.save(toUpdatedLocality);
		return localityMapper.localityToDto(updatedLocality);

	}

	@Override
	public Boolean deleteLocality(Long localityId, Long userId)
			throws ResourceNotFoundException, NotAuthorizedException {

		if (!this.fetchUserService.chechIsUserAdmin(userId)) {
			throw new NotAuthorizedException("User is not authorised");
		}

		Optional<Locality> doesLocalityExist = this.localityRepository.findById(localityId);

		if (doesLocalityExist.isEmpty()) {

			throw new ResourceNotFoundException("Locality not found");
		}

		this.localityRepository.deleteById(localityId);
		return true;
	}

	@Override
	public Locality doesLocalityExist(String localityName) {
		return this.localityRepository.findByName(localityName);
	}

}
