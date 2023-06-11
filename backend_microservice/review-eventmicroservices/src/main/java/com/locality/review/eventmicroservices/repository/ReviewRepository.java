package com.locality.review.eventmicroservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.locality.review.eventmicroservices.entity.Review;
import org.springframework.data.domain.Sort;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<Review> findTop10ByOrderByDateDesc();
	
	List<Review> findAllByOrderByDateDesc();
	
	List<Review> findByUserId(Long userId, Sort sortByDateDesc);
	
	List<Review> findByLocalityId(Long localityId, Sort sortByDateDesc);
	
	List<Review> findByUserIdAndLocalityId(Long userId, Long localityId, Sort sortByDateDesc);


}