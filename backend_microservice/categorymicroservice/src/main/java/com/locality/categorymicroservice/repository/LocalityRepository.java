package com.locality.categorymicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.locality.categorymicroservice.entity.Locality;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long>{
	
	Locality findByName(String name);
	
	@Modifying
	@Query(value = "ALTER TABLE localities AUTO_INCREMENT = 1;", nativeQuery = true)
	void resetAutoIncrement();
	
}
