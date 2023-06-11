package com.locality.review.eventmicroservices.service;

import java.util.List;

import com.locality.review.eventmicroservices.entity.Review;
import com.locality.review.eventmicroservices.payload.ReviewDto;

public interface ReviewService {

	public ReviewDto saveReview(ReviewDto reviewDto, String token, Long localityId);

	public List<ReviewDto> getAllReview();

	public List<ReviewDto> getRecentReview();

	public List<ReviewDto> getAllReviewByLocality(Long localityId);

	public List<ReviewDto> getAllReviewByUser(String token);
	
	public List<ReviewDto> getAllReviewByUserAndLocality(String token, Long localityId);

	public ReviewDto updateReview(ReviewDto reviewDto, Long reviewId, String token);

	public Boolean deleteReview(Long reviewId, String token);
}
