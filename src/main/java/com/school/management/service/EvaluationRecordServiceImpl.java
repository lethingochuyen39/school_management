package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.EvaluationRecordDto;
import com.school.management.model.EvaluationRecord;
import com.school.management.model.Student;
import com.school.management.repository.EvaluationRecordRepository;
import com.school.management.repository.StudentRepository;

@Service
public class EvaluationRecordServiceImpl implements EvaluationRecordService{

    @Autowired 
    private EvaluationRecordRepository evaluationRecordRepository; 
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public EvaluationRecord createEvaluationRecord(EvaluationRecordDto evaluationRecordDto) {
        Long studentId = evaluationRecordDto.getStudentId();

        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));
        
        EvaluationRecord evaluationRecord = new EvaluationRecord();
        evaluationRecord.setStudent(student);
        evaluationRecord.setDisciplineReason(evaluationRecordDto.getDisciplineReason());
        evaluationRecord.setAchievement(evaluationRecordDto.getAchievement());
        evaluationRecord.setDate(evaluationRecordDto.getDate());

        return evaluationRecordRepository.save(evaluationRecord);
    }

    @Override
    public EvaluationRecord updateEvaluationRecord(Long id, EvaluationRecordDto evaluationRecordDto) {
        EvaluationRecord exitingEvaluationRecord = evaluationRecordRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Evaluation Record not found with id: " + id));
        exitingEvaluationRecord.setDisciplineReason(evaluationRecordDto.getDisciplineReason());
        exitingEvaluationRecord.setAchievement(evaluationRecordDto.getAchievement());
        exitingEvaluationRecord.setDate(evaluationRecordDto.getDate());

        return evaluationRecordRepository.save(exitingEvaluationRecord);
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
