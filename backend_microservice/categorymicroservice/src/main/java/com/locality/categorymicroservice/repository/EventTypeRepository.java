package com.locality.categorymicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.locality.categorymicroservice.entity.EventType;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {

	EventType findByTypeOfEvent(String eventTypeName);
	
	@Modifying
	@Query(value = "ALTER TABLE typeofevents AUTO_INCREMENT = 1;", nativeQuery = true)
	void resetAutoIncrement();

}
