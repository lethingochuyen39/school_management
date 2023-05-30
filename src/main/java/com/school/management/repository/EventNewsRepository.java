package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.EventNews;

public interface EventNewsRepository extends JpaRepository<EventNews, Long> {

	List<EventNews> findByTitleContainingIgnoreCase(String title);

	boolean existsByTitle(String title);
}