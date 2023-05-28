package com.school.management.service;

import com.school.management.model.ScoreType;

import java.util.List;

public interface ScoreTypeService {
	List<ScoreType> getAllScoreTypes();

	ScoreType getScoreTypeById(Long id);

	ScoreType createScoreType(ScoreType scoreType);

	ScoreType updateScoreType(Long id, ScoreType scoreType);

	void deleteScoreType(Long id);
}