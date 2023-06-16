package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByNameContainingIgnoreCase(String name);
}
