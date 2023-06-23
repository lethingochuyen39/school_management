package com.school.management.service;

import com.school.management.dto.ScoreDTO;
import com.school.management.model.Score;

import java.util.List;

public interface ScoreService {
	List<Score> getAllScores();

	Score getScoreById(Long id);

	Score createScore(ScoreDTO scoreDTO);

	Score updateScore(Long id, ScoreDTO scoreDTO);

	void deleteScore(Long id);

	List<Score> searchScoresByStudentName(String studentName);

	List<Score> searchScoresByStudentId(Long studentId);

	List<Score> searchScore(String studentName);
}