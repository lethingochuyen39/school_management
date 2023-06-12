package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.EvaluationRecord;
import com.school.management.repository.EvaluationRecordRepository;

@Service
public class EvaluationRecordServiceImpl implements EvaluationRecordService{

    @Autowired 
    private EvaluationRecordRepository evaluationRecordRepository; 

    @Override
    public EvaluationRecord createEvaluationRecord(EvaluationRecord evaluationRecord) {
        if (evaluationRecord.getStudent() == null || evaluationRecord.getDisciplineReason() == null 
            || evaluationRecord.getAchievement() == null || evaluationRecord.getDate() == null) {
			throw new IllegalArgumentException("Student Id, Discipline Reason, start date, and end date are required.");
		}

		if (evaluationRecordRepository.existsById(evaluationRecord.getId())) {
			throw new IllegalArgumentException("Evaluation Record with the same id already exists.");
		}

		return evaluationRecordRepository.save(evaluationRecord);
    }

    @Override
    public EvaluationRecord getEvaluationRecordById(Long id) {
        return evaluationRecordRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Evaluation Record not found with id: " + id));
    }

    @Override
    public List<EvaluationRecord> getEvaluationRecordByStudentId(Long studentId) {
        return evaluationRecordRepository.findByStudentId(studentId);
    }

    @Override
    public EvaluationRecord updateEvaluationRecord(Long id, EvaluationRecord evaluationRecord) {
        if (!evaluationRecordRepository.existsById(id)) {
            throw new EvaluationRecordNotFoundException("Evaluation Record not found with id: " + id);
        }

        if (evaluationRecord.getStudent() == null || evaluationRecord.getDisciplineReason() == null 
            || evaluationRecord.getAchievement() == null || evaluationRecord.getDate() == null) {
			throw new IllegalArgumentException("Student Id, Discipline Reason, start date, and end date are required.");
		}

        EvaluationRecord existingEvaluationRecord = evaluationRecordRepository.findById(id)
                .orElseThrow(() -> new EvaluationRecordNotFoundException("Evaluation Record not found with id: " + id));

        if (existingEvaluationRecord != null && !existingEvaluationRecord.getId().equals(evaluationRecord.getId())) {
            if (evaluationRecordRepository.existsById(evaluationRecord.getId())) {
                throw new IllegalArgumentException("Evaluation Record is already exists.");
            }
        }
        evaluationRecord.setId(id);
        return evaluationRecordRepository.save(evaluationRecord);
    }

    @Override
    public void deleteEvaluationRecord(Long id) {
        if (!evaluationRecordRepository.existsById(id)) {
			throw new IllegalArgumentException("Evaluation Record not found with id: " + id);
		}
		evaluationRecordRepository.deleteById(id);
    }

    @Override
    public List<EvaluationRecord> getEvaluationRecords() {
        return evaluationRecordRepository.findAll();
    }
    
    public class EvaluationRecordNotFoundException extends RuntimeException {
        public EvaluationRecordNotFoundException(String message) {
            super(message);
        }
    }
}
