package com.school.management.service;

import java.util.List;

import com.school.management.model.EvaluationRecord;

public interface EvaluationRecordService {
    EvaluationRecord createEvaluationRecord(EvaluationRecord evaluationRecord);

	EvaluationRecord getEvaluationRecordById(Long id);

	List<EvaluationRecord> getEvaluationRecordByStudentId(Long studentId);

	EvaluationRecord updateEvaluationRecord(Long id, EvaluationRecord evaluationRecord);

	void deleteEvaluationRecord(Long id);

	List<EvaluationRecord> getEvaluationRecords();

	// List<EvaluationRecord> getEvaluationRecordsByName(String name);
    
}
