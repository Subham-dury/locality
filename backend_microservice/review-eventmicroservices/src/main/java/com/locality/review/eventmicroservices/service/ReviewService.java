package com.locality.review.eventmicroservices.service;

import java.util.List;

import com.locality.review.eventmicroservices.entity.Review;
import com.locality.review.eventmicroservices.payload.ReviewDto;

public interface ReviewService {

	public ReviewDto saveReview(ReviewDto reviewDto, Long userId, Long localityId);

	public List<ReviewDto> getAllReview();

	public List<ReviewDto> getRecentReview();

	public List<ReviewDto> getAllReviewByLocality(Long localityId);

	public List<ReviewDto> getAllReviewByUser(Long userId);

	public ReviewDto updateReview(ReviewDto reviewDto, Long reviewId);

	public boolean deleteReview(Long reviewId);

	public Review dtoToReview(ReviewDto reviewDto);

	public ReviewDto reviewToDto(Review review);
}
