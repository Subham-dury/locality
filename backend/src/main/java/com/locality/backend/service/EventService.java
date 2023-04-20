package com.locality.backend.service;

import java.util.List;

import com.locality.backend.entity.Event;
import com.locality.backend.entity.Review;

public interface EventService {
	
	public Event saveEvent(Event event, Long userId, Long localityId);
	
	public List<Event> getAllEvent();
	
	public List<Event> getRecentEvent();
	
	public List<Event> getAllEventByLocality(Long localityId);
	
	public List<Event> getAllEventByUser(Long userId);
	
	public Event updateEvent(Event event, Long id);
	
	public boolean deleteEvent(Long id);

	
}
