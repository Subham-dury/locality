package com.locality.backend.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locality.backend.entity.Locality;
import com.locality.backend.entity.Review;
import com.locality.backend.entity.User;
import com.locality.backend.exception.EntityNotFoundException;
import com.locality.backend.repository.ReviewRepository;
import com.locality.backend.service.LocalityService;
import com.locality.backend.service.ReviewService;
import com.locality.backend.service.UserService;

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
	public Review saveReview(Review review, Long userId, Long localityId) {
		
		User user = userService.getUserById(userId);
		
		if(user == null) {
			log.info("User not found");
			throw new EntityNotFoundException("User not found");
		}
		
		Locality locality = localityService.getLocalityById(localityId);
		
		if(locality == null) {
			log.info("Locality not found");
			throw new EntityNotFoundException("Locality not found");
		}
		
		log.info("Saving new review");
		review.setImg((int) (Math.random() * 4) + 1);
		review.setDate(LocalDate.now());
		review.setUser(user);
		review.setLocality(locality);
		return (this.reviewRepository.save(review));
	}

	@Override
	public List<Review> getAllReview() {
		
		List<Review> reviews = this.reviewRepository.findAll();
		
		reviews.forEach(review -> System.out.println(review.getContent()));
		
		if (reviews.isEmpty()) {
			log.info("Reviews not found");
			throw new EntityNotFoundException("Reviews not found");
		}
		
		return reviews;
	}
	
	@Override
	public List<Review> getRecentReview() {
		
		List<Review> reviews = this.reviewRepository.findTop10ByOrderByDateDesc();
		
		reviews.forEach(review -> System.out.println(review.getContent()));
		

		if (reviews.isEmpty()) {
			log.info("Reviews not found");
			throw new EntityNotFoundException("Reviews not found");
		}
		
		return reviews;
	}
	

	@Override
	public List<Review> getAllReviewByLocality(Long localityId) {
		
		Locality locality = localityService.getLocalityById(localityId);
		
		List<Review> reviews = reviewRepository.findByLocality(locality);
		
		reviews.forEach(review -> System.out.println(review.getContent()));
		
		if (reviews.isEmpty()) {
			log.info("Reviews not found");
			throw new EntityNotFoundException("Reviews not found");
		}
		return reviews;
	}

	@Override
	public List<Review> getAllReviewByUser(Long userId) {
		
		User user = userService.getUserById(userId);
		
		List<Review> reviews = reviewRepository.findByUser(user);
		
		reviews.forEach(review -> System.out.println(review.getContent()));
		
		if (reviews.isEmpty()) {
			log.info("Reviews not found");
			throw new EntityNotFoundException("Reviews not found");
		}
		return reviews;
	}

	@Override
	public Review updateReview(Review review, Long id) {
		
		Optional<Review> searchedReview = reviewRepository.findById(id);
		
		if(searchedReview.isEmpty()) {
			log.info("Review not found");
			throw new EntityNotFoundException("Review not found");
		}
		
		Review updatedReview = searchedReview.get();
		updatedReview.setContent(review.getContent() == null ?
				updatedReview.getContent() : review.getContent());
		
		return updatedReview;
	}

	@Override
	public boolean deleteReview(Long id) {
		Optional<Review> doesReviewExist = reviewRepository.findById(id);

		if (doesReviewExist.isEmpty()) {
			log.info("Review not found");
			throw new EntityNotFoundException("Review not found");
		}

		reviewRepository.deleteById(id);
		return true;
	}


}
