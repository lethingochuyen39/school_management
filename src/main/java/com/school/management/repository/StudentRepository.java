package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.management.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

    // huyen
    @Query("SELECT s FROM Student s WHERE s.className.id = :classId")
    List<Student> findByClassId(@Param("classId") Long classId);
}