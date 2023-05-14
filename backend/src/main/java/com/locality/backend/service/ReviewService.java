package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.Review;

public interface ReviewService {
	
	public Review saveReview(Review review, Long userId, Long localityId);
	
	public List<Review> getAllReview();
	
	public List<Review> getRecentReview();
	
	public List<Review> getAllReviewByLocality(Long localityId);
	
	public List<Review> getAllReviewByUser(Long userId);
	
	public Review updateReview(Review review, Long reviewId);
	
	public boolean deleteReview(Long localityId);
}
