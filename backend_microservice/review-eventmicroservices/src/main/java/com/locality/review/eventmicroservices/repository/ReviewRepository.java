package com.locality.review.eventmicroservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.locality.review.eventmicroservices.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<Review> findTop10ByOrderByDate();
	
	List<Review> findByOrderByDate();
	
	List<Review> findByUserIdOrderByDate(Long userId);
	
	List<Review> findByLocalityIdOrderByDate(Long localityId);
	
	List<Review> findByUserIdAndLocalityIdOrderByDate(Long userId, Long localityId);
	
}