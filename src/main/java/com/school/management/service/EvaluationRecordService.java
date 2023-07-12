package com.school.management.service;

import java.util.List;

import com.school.management.dto.EvaluationRecordDto;
import com.school.management.model.EvaluationRecord;

public interface EvaluationRecordService {
    EvaluationRecord createEvaluationRecord(EvaluationRecordDto evaluationRecordDto);

	EvaluationRecord getEvaluationRecordById(Long id);

	List<EvaluationRecord> getEvaluationRecordByStudentId(Long studentId);

	EvaluationRecord updateEvaluationRecord(Long id, EvaluationRecordDto evaluationRecordDto);

	void deleteEvaluationRecord(Long id);

	List<EvaluationRecord> getEvaluationRecords();

	// List<EvaluationRecord> getEvaluationRecordsByName(String name);
    
}
