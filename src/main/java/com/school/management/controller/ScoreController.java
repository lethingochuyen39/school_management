package com.school.management.controller;

import com.school.management.dto.ScoreDTO;
import com.school.management.model.Score;
import com.school.management.service.ScoreServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

	@Autowired
	private ScoreServiceImpl scoreServiceImpl;

	@GetMapping
	public ResponseEntity<List<?>> getAllScores(@RequestParam(required = false) String studentName) {
		List<Score> scores = scoreServiceImpl.searchScore(studentName);
		return ResponseEntity.ok(scores);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getScoreById(@PathVariable("id") Long id) {
		try {
			Score score = scoreServiceImpl.getScoreById(id);
			return ResponseEntity.ok(score);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> createScore(@RequestBody ScoreDTO scoreDTO) {
		try {
			Score createdScore = scoreServiceImpl.createScore(scoreDTO);
			return ResponseEntity.ok(createdScore);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateScore(@PathVariable("id") Long id, @RequestBody ScoreDTO scoreDTO) {
		try {
			Score updatedScore = scoreServiceImpl.updateScore(id, scoreDTO);
			return ResponseEntity.ok(updatedScore);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteScore(@PathVariable("id") Long id) {
		try {
			scoreServiceImpl.deleteScore(id);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/search/student")
	public ResponseEntity<List<Score>> searchScoresByStudentName(@RequestParam("name") String studentName) {
		List<Score> scores = scoreServiceImpl.searchScoresByStudentName(studentName);
		return ResponseEntity.ok(scores);
	}

	@GetMapping("/search/studentId")
	public ResponseEntity<List<Score>> searchScoresByStudentId(@RequestParam("id") Long studentId) {
		List<Score> scores = scoreServiceImpl.searchScoresByStudentId(studentId);
		return ResponseEntity.ok(scores);
	}

}