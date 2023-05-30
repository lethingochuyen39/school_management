package com.school.management.controller;

import com.school.management.model.EventNews;
import com.school.management.service.EventNewsServiceImpl;
import com.school.management.service.EventNewsServiceImpl.EventNewsNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/eventNews")
public class EventNewsController {
	@Autowired
	private EventNewsServiceImpl eventNewsServiceImpl;

	@PostMapping("/add")
	public ResponseEntity<?> createEventNews(@ModelAttribute EventNews eventNews,
			@RequestPart("image") MultipartFile image) {
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
			@RequestPart("image") MultipartFile image) {
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
	public ResponseEntity<List<EventNews>> getAllEventNews() {
		List<EventNews> eventNewsList = eventNewsServiceImpl.getAllEventNews();
		return ResponseEntity.ok(eventNewsList);
	}

	@GetMapping("/search")
	public ResponseEntity<List<EventNews>> searchEventNewsByTitle(@RequestParam("title") String title) {
		List<EventNews> eventNewsList = eventNewsServiceImpl.searchEventNewsByTitle(title);
		return ResponseEntity.ok(eventNewsList);
	}
}