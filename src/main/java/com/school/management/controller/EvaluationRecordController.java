package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.model.EvaluationRecord;
import com.school.management.service.EvaluationRecordServiceImpl;

@RestController
@RequestMapping("/api/evaluation_records")
public class EvaluationRecordController {
    @Autowired
    private EvaluationRecordServiceImpl evaluationRecordServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createEvaluationRecord(@RequestBody EvaluationRecord classes) {
        try {
            EvaluationRecord createdEvaluationRecord = evaluationRecordServiceImpl.createEvaluationRecord(classes);
            return ResponseEntity.ok().body(createdEvaluationRecord);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findById/{id}")
	public ResponseEntity<?> getEvaluationRecordById(@PathVariable Long id) {
		try {
			EvaluationRecord evaluationRecord = evaluationRecordServiceImpl.getEvaluationRecordById(id);
			return ResponseEntity.ok(evaluationRecord);
		} catch (EvaluationRecordServiceImpl.EvaluationRecordNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEvaluationRecord(@PathVariable Long id,
			@RequestBody EvaluationRecord classes) {
		try {
			EvaluationRecord updatedClasses = evaluationRecordServiceImpl.updateEvaluationRecord(id, classes);
			return ResponseEntity.ok(updatedClasses);
		} catch (EvaluationRecordServiceImpl.EvaluationRecordNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEvaluationRecord(@PathVariable Long id) {
		try {
			evaluationRecordServiceImpl.deleteEvaluationRecord(id);
			return ResponseEntity.ok().build();
		} catch (EvaluationRecordServiceImpl.EvaluationRecordNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<EvaluationRecord>> getEvaluationRecord() {
		List<EvaluationRecord> classes = evaluationRecordServiceImpl.getEvaluationRecords();
		return ResponseEntity.ok(classes);
	}

	@GetMapping("/search/studentId")
	public ResponseEntity<List<EvaluationRecord>> searchScoresByStudentId(@RequestParam("id") Long studentId) {
		List<EvaluationRecord> evaluationRecord = evaluationRecordServiceImpl.getEvaluationRecordByStudentId(studentId);
		return ResponseEntity.ok(evaluationRecord);
	}
}
