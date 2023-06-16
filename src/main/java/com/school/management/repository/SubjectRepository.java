package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Subject;
import com.school.management.model.Teacher;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
	Subject findById(long id);

	boolean existsByName(String name);

	boolean existsByTeacher(Teacher teacher);

    List<Subject> findByNameContainingIgnoreCase(String name);
}