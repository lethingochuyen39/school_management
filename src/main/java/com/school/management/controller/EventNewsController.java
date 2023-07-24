package com.school.management.controller;

import com.school.management.model.EventNews;
import com.school.management.service.EventNewsServiceImpl;
import com.school.management.service.EventNewsServiceImpl.EventNewsNotFoundException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class EventNewsController {
	@Autowired
	private EventNewsServiceImpl eventNewsServiceImpl;
	private static final String UPLOAD_FOLDER = "uploads/news/";

	@PostMapping()
	public ResponseEntity<?> createEventNews(@ModelAttribute EventNews eventNews,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		try {
			EventNews addEventNews = eventNewsServiceImpl.createEventNews(eventNews, image);
			return ResponseEntity.ok(addEventNews);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (EventNewsNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEventNewsById(@PathVariable("id") Long id) {
		try {
			EventNews eventNews = eventNewsServiceImpl.getEventNewsById(id);
			return ResponseEntity.ok(eventNews);
		} catch (EventNewsNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<?> updateEventNews(@PathVariable("id") Long id,
			@ModelAttribute EventNews eventNews,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		try {
			EventNews updatedEventNews = eventNewsServiceImpl.updateEventNews(id, eventNews, image);
			return ResponseEntity.ok(updatedEventNews);
		} catch (EventNewsNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEventNews(@PathVariable("id") Long id) {
		boolean isDeleted = eventNewsServiceImpl.deleteEventNews(id);
		if (isDeleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<List<?>> getAllEventNews(@RequestParam(required = false) String title) {
		List<EventNews> eventNewsList = eventNewsServiceImpl.searchNews(title);
		return ResponseEntity.ok(eventNewsList);
	}

	@GetMapping("/search")
	public ResponseEntity<List<EventNews>> searchEventNewsByTitle(@RequestParam("title") String title) {
		List<EventNews> eventNewsList = eventNewsServiceImpl.searchEventNewsByTitle(title);
		return ResponseEntity.ok(eventNewsList);
	}

	@PutMapping("/isActive/{id}")
	public ResponseEntity<?> updateIsActive(@PathVariable("id") Long id) {
		try {
			EventNews updatedEventNews = eventNewsServiceImpl.updateNewsStatus(id);
			return ResponseEntity.ok(updatedEventNews);
		} catch (EventNewsNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	//duy
	@GetMapping("/allNew")
	public ResponseEntity<List<?>> getAllEventNews2() {
		List<EventNews> eventNewsList = eventNewsServiceImpl.getAllEventNews();
		return ResponseEntity.ok(eventNewsList);
	}
}
