package com.locality.review.eventmicroservices.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.locality.review.eventmicroservices.payload.ReviewDto;
import com.locality.review.eventmicroservices.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	
	@PostMapping("/")
	public ResponseEntity<ReviewDto> saveReview(@Valid @RequestBody ReviewDto review, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
				@RequestParam Long localityId){
		
		return new ResponseEntity<ReviewDto>(this.reviewService.saveReview(review, token, localityId)
				,HttpStatus.CREATED);
			
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ReviewDto>> findAllReview(){
		
		return ResponseEntity.ok(this.reviewService.getAllReview());
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<ReviewDto>> findRecentReview(){
		
		return ResponseEntity.ok(this.reviewService.getRecentReview());
	}
	
	@GetMapping("/byuser")
	public ResponseEntity<List<ReviewDto>> findReviewByUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
		
		return ResponseEntity.ok(this.reviewService.getAllReviewByUser(token));
	}
	
	@GetMapping("/bylocality/{localityId}")
	public ResponseEntity<List<ReviewDto>> findReviewByLocality(@PathVariable(name="localityId") String localityId){
		
		
		System.out.println("inside locality");
		return ResponseEntity.ok(this.reviewService.getAllReviewByLocality(Long.parseLong(localityId)));
	}
	
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<ReviewDto> updateReview(@RequestBody ReviewDto review, 
				@PathVariable(name="reviewId") String reviewId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
		
		return ResponseEntity.ok(this.reviewService.updateReview(review, Long.parseLong(reviewId), token));
	}
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> deleteReview(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
			@PathVariable(name="reviewId") String reviewId){
		
		boolean deleteReview = this.reviewService.deleteReview(Long.parseLong(reviewId), token);
		if(deleteReview) {
			return ResponseEntity.ok(Map.of("message", "Review deleted successfully"));
		}
		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
	
	
	
}
