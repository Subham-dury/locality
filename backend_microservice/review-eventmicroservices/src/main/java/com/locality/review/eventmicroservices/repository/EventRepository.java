package com.locality.review.eventmicroservices.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.locality.review.eventmicroservices.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findTop10ByOrderByPostDate();

	List<Event> findAllByOrderByPostDate();

	List<Event> findByUserId(Long userId, Sort sortByDateDesc);

	List<Event> findByLocalityId(Long localityId, Sort sortByDateDesc);

	List<Event> findByEventTypeId(Long eventTypeId, Sort sortByDateDesc);
	
	List<Event> findByLocalityIdAndEventTypeId(Long localityId, Long eventTypeId, Sort sortByDateDesc);
	
	List<Event> findByUserIdAndLocalityId(Long userId, Long localityId, Sort sortByDateDesc);

	List<Event> findByUserIdAndLocalityIdAndEventTypeId(Long userId, Long localityId,
			Long eventTypeId, Sort sortByDateDesc);

}
