package com.locality.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.locality.backend.entity.Event;
import com.locality.backend.entity.EventType;
import com.locality.backend.entity.Locality;
import com.locality.backend.entity.User;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findTop10ByOrderByPostDateDesc();

	List<Event> findByUser(User user);

	List<Event> findByLocality(Locality locality);

	List<Event> findByEventType(EventType eventType);
	
	List<Event> findByUserAndLocalityAndEventType(User user, Locality locality,
			EventType eventType);

	@Modifying
	@Query(value = "ALTER TABLE events AUTO_INCREMENT = 1;", nativeQuery = true)
	void resetAutoIncrement();
}
