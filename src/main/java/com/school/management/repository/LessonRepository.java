package com.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
	Lesson findByName(String name);
}
