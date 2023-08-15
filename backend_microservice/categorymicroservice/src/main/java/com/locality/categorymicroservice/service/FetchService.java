package com.locality.categorymicroservice.service;

public interface FetchService {
	
	public Boolean checkIsUserAdmin(String token);

	public Boolean deleteReviews(String token, String localityId);

	public Boolean deleteEvents(String token, String localityId);

}
