package com.locality.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locality.backend.entity.Locality;

public interface LocalityRepository extends JpaRepository<Locality, Long>{
	
	Locality findByName(String name);
	
}
