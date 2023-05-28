package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.ScoreType;
import com.school.management.repository.ScoreTypeRepository;

@Service
public class ScoreTypeServiceImpl implements ScoreTypeService {
	@Autowired
	private ScoreTypeRepository scoreTypeRepository;

	@Override
	public List<ScoreType> getAllScoreTypes() {
		return scoreTypeRepository.findAll();
	}

	@Override
	public ScoreType getScoreTypeById(Long id) {
		return scoreTypeRepository.findById(id)
				.orElseThrow(() -> new ScoreTypeNotFoundException("ScoreType not found with id: " + id));
	}

	@Override
	public ScoreType createScoreType(ScoreType scoreType) {
		if (scoreTypeRepository.existsByName(scoreType.getName())) {
			throw new IllegalArgumentException("ScoreType with the same name already exists.");
		}
		return scoreTypeRepository.save(scoreType);
	}

	@Override
	public ScoreType updateScoreType(Long id, ScoreType scoreType) {
		ScoreType existingScoreType = scoreTypeRepository.findById(id)
				.orElseThrow(() -> new ScoreTypeNotFoundException("ScoreType not found with id: " + id));

		if (!existingScoreType.getName().equals(scoreType.getName()) &&
				scoreTypeRepository.existsByName(scoreType.getName())) {
			throw new IllegalArgumentException("ScoreType with the same name already exists.");
		}

		existingScoreType.setName(scoreType.getName());
		return scoreTypeRepository.save(existingScoreType);
	}

	@Override
	public void deleteScoreType(Long id) {
		if (!scoreTypeRepository.existsById(id)) {
			throw new ScoreTypeNotFoundException("ScoreType not found with id: " + id);
		}
		scoreTypeRepository.deleteById(id);
	}

	public class ScoreTypeNotFoundException extends RuntimeException {
		public ScoreTypeNotFoundException(String message) {
			super(message);
		}
	}

}