package com.locality.review.eventmicroservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.locality.review.eventmicroservices.entity.Event;
import com.locality.review.eventmicroservices.entity.Review;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findTop10ByOrderByPostDate();

	List<Event> findByOrderByPostDate();

	List<Event> findByUserIdOrderByPostDate(Long userId);

	List<Event> findByLocalityIdOrderByPostDate(Long localityId);

	List<Event> findByEventTypeIdOrderByPostDate(Long eventTypeId);

	List<Event> findByUserIdAndLocalityIdAndEventTypeIdOrderByPostDate(Long userId, Long localityId,
			Long eventTypeId);

}
