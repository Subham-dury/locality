package com.locality.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.locality.backend.entity.Review;
import com.locality.backend.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	
	@PostMapping("/")
	public ResponseEntity<Review> saveReview(@RequestBody Review review, @RequestParam Long userId,
				@RequestParam Long localityId){
		
		return new ResponseEntity<Review>(this.reviewService.saveReview(review, userId, localityId)
				,HttpStatus.CREATED);
			
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Review>> findAllReview(){
		
		return ResponseEntity.ok(this.reviewService.getAllReview());
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<Review>> findRecentReview(){
		
		return ResponseEntity.ok(this.reviewService.getRecentReview());
	}
	
	@GetMapping("/byuser/{userId}")
	public ResponseEntity<List<Review>> findReviewByUserid(@PathVariable String userId){
		
		return ResponseEntity.ok(this.reviewService.getAllReviewByUser(Long.parseLong(userId)));
	}
	
	@GetMapping("/bylocality/{localityId}")
	public ResponseEntity<List<Review>> findReviewByLocalityid(@PathVariable String localityId){
		
		return ResponseEntity.ok(this.reviewService.getAllReviewByLocality(Long.parseLong(localityId)));
	}
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<Review> updateReview(@RequestBody Review review, 
				@PathVariable String reviewId){
		
		return ResponseEntity.ok(this.reviewService.updateReview(review, Long.parseLong(reviewId)));
	}
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> deleteReview(@PathVariable String reviewId){
		
		boolean deleteReview = this.reviewService.deleteReview(Long.parseLong(reviewId));
		if(deleteReview) {
			return ResponseEntity.ok(Map.of("message", "Review deleted successfully"));
		}
		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
	
	
	
}
