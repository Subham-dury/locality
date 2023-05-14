package com.locality.backend.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Locality;
import com.locality.backend.entity.Review;
import com.locality.backend.entity.User;
import com.locality.backend.exception.DataExistsException;
import com.locality.backend.exception.DataNotFoundException;
import com.locality.backend.repository.ReviewRepository;
import com.locality.backend.service.LocalityService;
import com.locality.backend.service.ReviewService;
import com.locality.backend.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private LocalityService localityService;

	@Autowired
	private UserService userService;

	@Override
	public Review saveReview(Review review, Long userId, Long localityId) 
			throws DataNotFoundException, DataExistsException{

		if (this.reviewRepository.findAll().isEmpty()) {
			this.reviewRepository.resetAutoIncrement();
		}

		User user = this.userService.getUserById(userId);

		if (user == null) {
			log.error("User not found");
			throw new DataNotFoundException("User not found");
		}

		Locality locality = this.localityService.getLocalityById(localityId);

		if (locality == null) {
			log.error("Locality not found");
			throw new DataNotFoundException("Locality not found");
		}

		if (!this.reviewRepository.findByUserAndLocality(user, locality).isEmpty()) {
			log.error("Duplicate data");
			throw new DataExistsException("You have already added a review for the selected locality");
		}

		log.info("Saving new review");
		review.setImg((int) (Math.random() * 4) + 1);
		review.setDate(LocalDate.now());
		review.setUser(user);
		review.setLocality(locality);
		return (this.reviewRepository.save(review));
	}

	@Override
	public List<Review> getAllReview() throws DataNotFoundException{

		List<Review> reviews = this.reviewRepository.findAll();

		if (reviews.isEmpty()) {
			log.error("Reviews not found");
			throw new DataNotFoundException("Reviews not found");
		}

		return reviews;
	}

	@Override
	public List<Review> getRecentReview() throws DataNotFoundException{

		List<Review> reviews = this.reviewRepository.findTop10ByOrderByDateDesc();

		if (reviews.isEmpty()) {
			log.error("Reviews not found");
			throw new DataNotFoundException("Reviews not found");
		}

		return reviews;
	}

	@Override
	public List<Review> getAllReviewByLocality(Long localityId) throws DataNotFoundException{

		Locality locality = this.localityService.getLocalityById(localityId);

		if (locality == null) {
			log.error("Locality not found");
			throw new DataNotFoundException("Locality not found");
		}

		List<Review> reviews = reviewRepository.findByLocality(locality);

		if (reviews.isEmpty()) {
			log.error("Reviews not found");
			throw new EntityNotFoundException("Reviews not found");
		}
		return reviews;
	}

	@Override
	public List<Review> getAllReviewByUser(Long userId) {

		User user = userService.getUserById(userId);

		if (user == null) {
			log.error("User not found");
			throw new EntityNotFoundException("User not found");
		}

		List<Review> reviews = reviewRepository.findByUser(user);

		if (reviews.isEmpty()) {
			log.error("Reviews not found");
			throw new EntityNotFoundException("Reviews not found");
		}
		return reviews;
	}

	@Override
	public Review updateReview(Review review, Long reviewId) {

		Optional<Review> searchedReview = reviewRepository.findById(reviewId);

		if (searchedReview.isEmpty()) {
			log.error("Review not found");
			throw new EntityNotFoundException("Review not found");
		}

		Review updatedReview = searchedReview.get();
		updatedReview.setContent(review.getContent() == null ?
				updatedReview.getContent() : review.getContent());

		return this.reviewRepository.save(updatedReview);
	}

	@Override
	public boolean deleteReview(Long reviewId) throws DataNotFoundException{
		Optional<Review> doesReviewExist = reviewRepository.findById(reviewId);

		if (doesReviewExist.isEmpty()) {
			log.error("Review not found");
			throw new DataNotFoundException("Review not found");
		}

		reviewRepository.deleteById(reviewId);
		return true;
	}
}
