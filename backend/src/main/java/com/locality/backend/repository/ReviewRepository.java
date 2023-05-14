package com.locality.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.locality.backend.entity.Locality;
import com.locality.backend.entity.Review;
import com.locality.backend.entity.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<Review> findTop10ByOrderByDateDesc();
	
	List<Review> findByUser(User user);
	
	List<Review> findByLocality(Locality locality);
	
	List<Review> findByUserAndLocality(User user, Locality locality);
	
	@Modifying
	@Query(value = "ALTER TABLE reviews AUTO_INCREMENT = 1;", nativeQuery = true)
	void resetAutoIncrement();

}