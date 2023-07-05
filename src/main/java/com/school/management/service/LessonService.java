package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.Lesson;
import com.school.management.repository.LessonRepository;

@Service
public class LessonService {

	@Autowired
	private LessonRepository lessonRepository;

	public Lesson createLesson(Lesson lesson) {
		// Kiểm tra các trường không được null
		if (lesson.getName() == null || lesson.getStartTime() == null || lesson.getEndTime() == null) {
			throw new IllegalArgumentException("Các trường không được trống");
		}

		// Kiểm tra thời gian kết thúc phải sau thời gian bắt đầu
		if (lesson.getEndTime().isBefore(lesson.getStartTime())) {
			throw new IllegalArgumentException("Thời gian kết thúc phải sau thời gian bắt đầu");
		}

		return lessonRepository.save(lesson);
	}

	public List<Lesson> getAllLessons() {
		return lessonRepository.findAll();
	}

}
