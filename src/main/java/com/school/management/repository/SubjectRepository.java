package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
	Subject findById(long id);

	boolean existsByName(String name);

    List<Subject> findByNameContainingIgnoreCase(String name);
}