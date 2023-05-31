package com.school.management.controller;

import com.school.management.model.ScoreType;
import com.school.management.service.ScoreTypeServiceImpl;
import com.school.management.service.ScoreTypeServiceImpl.ScoreTypeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score-types")
public class ScoreTypeController {

	@Autowired
	private ScoreTypeServiceImpl scoreTypeService;

	@GetMapping
	public ResponseEntity<List<ScoreType>> getAllScoreTypes() {
		List<ScoreType> scoreTypes = scoreTypeService.getAllScoreTypes();
		return ResponseEntity.ok(scoreTypes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getScoreTypeById(@PathVariable("id") Long id) {
		try {
			ScoreType scoreType = scoreTypeService.getScoreTypeById(id);
			return ResponseEntity.ok(scoreType);
		} catch (ScoreTypeNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> createScoreType(@RequestBody ScoreType scoreType) {
		try {
			ScoreType createdScoreType = scoreTypeService.createScoreType(scoreType);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdScoreType);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateScoreType(@PathVariable("id") Long id,
			@RequestBody ScoreType scoreType) {
		try {
			ScoreType updatedScoreType = scoreTypeService.updateScoreType(id, scoreType);
			return ResponseEntity.ok(updatedScoreType);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (ScoreTypeNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteScoreType(@PathVariable("id") Long id) {
		try {
			scoreTypeService.deleteScoreType(id);
			return (ResponseEntity<?>) ResponseEntity.ok();
		} catch (ScoreTypeNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
