package com.locality.categorymicroservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.locality.categorymicroservice.entity.Locality;
import com.locality.categorymicroservice.payload.LocalityAndEventTypeDto;
import com.locality.categorymicroservice.payload.LocalityDto;

@Component
public class LocalityMapper {

	@Autowired
	private ModelMapper modelMapper;

	public LocalityDto localityToDto(Locality locality) {
		return this.modelMapper.map(locality, LocalityDto.class);
	}

	public Locality dtoToLocality(LocalityDto localityDto) {
		return this.modelMapper.map(localityDto, Locality.class);
	}

	public LocalityAndEventTypeDto localityToDtoUsingIdAndName(Locality locality) {

		return LocalityAndEventTypeDto.builder().localityId(locality.getLocalityId()).name(locality.getName()).build();
	}

}
