package com.locality.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locality.backend.entity.Event;
import com.locality.backend.entity.Locality;
import com.locality.backend.entity.User;

public interface EventRepository extends JpaRepository<Event, Long>{
	
	List<Event> findTop10ByOrderByPostDateDesc();
	
	List<Event> findByUser(User user);
	
	List<Event> findByLocality(Locality locality);
	
}
