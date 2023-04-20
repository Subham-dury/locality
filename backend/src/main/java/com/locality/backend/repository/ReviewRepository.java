package com.locality.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locality.backend.entity.Locality;
import com.locality.backend.entity.Review;
import com.locality.backend.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<Review> findTop10ByOrderByDateDesc();
	
	List<Review> findByUser(User user);
	
	List<Review> findByLocality(Locality locality);

}
