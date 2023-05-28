package com.school.management.controller;

import com.school.management.dto.ScoreDTO;
import com.school.management.model.Score;
import com.school.management.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

	@Autowired
	private ScoreService scoreService;

	@GetMapping
	public ResponseEntity<List<Score>> getAllScores() {
		List<Score> scores = scoreService.getAllScores();
		return ResponseEntity.ok(scores);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getScoreById(@PathVariable("id") Long id) {
		try {
			Score score = scoreService.getScoreById(id);
			return ResponseEntity.ok(score);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/{add}")
	public ResponseEntity<?> createScore(@RequestBody ScoreDTO scoreDTO) {
		try {
			Score createdScore = scoreService.createScore(scoreDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdScore);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateScore(@PathVariable("id") Long id, @RequestBody ScoreDTO scoreDTO) {
		try {
			Score updatedScore = scoreService.updateScore(id, scoreDTO);
			return ResponseEntity.ok(updatedScore);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteScore(@PathVariable("id") Long id) {
		try {
			scoreService.deleteScore(id);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/search/student")
	public ResponseEntity<List<Score>> searchScoresByStudentName(@RequestParam("name") String studentName) {
		List<Score> scores = scoreService.searchScoresByStudentName(studentName);
		return ResponseEntity.ok(scores);
	}

	@GetMapping("/search/studentId")
	public ResponseEntity<List<Score>> searchScoresByStudentId(@RequestParam("id") Long studentId) {
		List<Score> scores = scoreService.searchScoresByStudentId(studentId);
		return ResponseEntity.ok(scores);
	}

	// @GetMapping("/search/class")
	// public ResponseEntity<List<Score>>
	// searchScoresByClassName(@RequestParam("name") String className) {
	// List<Score> scores = scoreService.searchScoresByClassName(className);
	// return ResponseEntity.ok(scores);
	// }
}