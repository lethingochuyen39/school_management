package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.EvaluationRecord;

public interface EvaluationRecordRepository extends JpaRepository<EvaluationRecord, Long>{

	List<EvaluationRecord> findByStudentId(Long studentId);

	List<EvaluationRecord> findByAchievementContainingIgnoreCase(String achievement);
}
