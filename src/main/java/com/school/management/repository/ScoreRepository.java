package com.school.management.repository;

import com.school.management.model.Score;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScoreRepository extends JpaRepository<Score, Long> {
	List<Score> findByStudentName(String studentName);

	List<Score> findByStudentId(Long studentId);

	@Query("SELECT sc FROM Score sc WHERE sc.student.className.id = :classId")
	List<Score> findByClassId(@Param("classId") Long classId);

}