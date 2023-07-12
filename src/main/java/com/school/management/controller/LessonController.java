package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.model.Lesson;
import com.school.management.service.LessonService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

	@Autowired
	private LessonService lessonService;

	@PostMapping
	public ResponseEntity<?> createLesson(@RequestBody Lesson lesson) {
		try {
			Lesson newLesson = lessonService.createLesson(lesson);
			return ResponseEntity.ok(newLesson);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Lesson>> getAllLessons() {
		List<Lesson> lessons = lessonService.getAllLessons();
		return ResponseEntity.ok(lessons);
	}

}
