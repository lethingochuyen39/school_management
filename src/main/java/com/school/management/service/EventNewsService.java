package com.school.management.service;

import com.school.management.model.EventNews;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface EventNewsService {
	EventNews createEventNews(EventNews eventNews, MultipartFile image);

	EventNews getEventNewsById(Long id);

	EventNews updateEventNews(Long id, EventNews eventNews, MultipartFile image);

	boolean deleteEventNews(Long id);

	List<EventNews> getAllEventNews();

	List<EventNews> searchEventNewsByTitle(String title);

	List<EventNews> searchNews(String title);

	EventNews updateNewsStatus(Long id);

}