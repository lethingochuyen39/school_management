package com.school.management.service;

import com.school.management.model.ScoreType;

import java.util.List;

public interface ScoreTypeService {

	ScoreType getScoreTypeById(Long id);

	ScoreType createScoreType(ScoreType scoreType);

	ScoreType updateScoreType(Long id, ScoreType scoreType);

	boolean deleteScoreType(Long id);

	List<ScoreType> getAllScoreTypes(String name);
}