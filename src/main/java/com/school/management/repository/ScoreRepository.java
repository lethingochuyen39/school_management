package com.school.management.repository;

import com.school.management.model.Score;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
	boolean existsByStudentIdAndSubjectIdAndScoreTypeId(Long studentId, Long subjectId, Long scoreTypeId);

	List<Score> findByStudentName(String studentName);

	List<Score> findByStudentId(Long studentId);

	// List<Score> findByStudentClassName(String className);

}