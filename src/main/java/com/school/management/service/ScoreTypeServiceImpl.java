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
	public List<ScoreType> getAllScoreTypes(String name) {
		if (name != null && !name.isEmpty()) {
			return scoreTypeRepository.findByNameContainingIgnoreCase(name);
		} else {
			return scoreTypeRepository.findAll();
		}
	}

	@Override
	public ScoreType getScoreTypeById(Long id) {
		return scoreTypeRepository.findById(id)
				.orElseThrow(() -> new ScoreTypeNotFoundException("Không tìm thấy loại điểm có id: " + id));
	}

	@Override
	public ScoreType createScoreType(ScoreType scoreType) {
		if (scoreTypeRepository.existsByName(scoreType.getName())) {
			throw new IllegalArgumentException("Loại điểm cùng tên đã tồn tại.");
		}
		if (scoreType.getCoefficient() == null) {
			throw new IllegalArgumentException("Vui lòng nhập Hệ số.");
		}

		if (scoreType.getCoefficient() < 0) {
			throw new IllegalArgumentException("Hệ số phải là số không âm.");
		}
		return scoreTypeRepository.save(scoreType);
	}

	@Override
	public ScoreType updateScoreType(Long id, ScoreType scoreType) {
		ScoreType existingScoreType = scoreTypeRepository.findById(id)
				.orElseThrow(() -> new ScoreTypeNotFoundException("Không tìm thấy loại diểm có id: " + id));

		if (!existingScoreType.getName().equals(scoreType.getName()) &&
				scoreTypeRepository.existsByName(scoreType.getName())) {
			throw new IllegalArgumentException("Loại điểm cùng tên đã tồn tại.");
		}

		if (scoreType.getCoefficient() == null) {
			throw new IllegalArgumentException("Vui lòng nhập Hệ số.");
		}

		if (scoreType.getCoefficient() < 0) {
			throw new IllegalArgumentException("Hệ số phải là số không âm.");
		}

		existingScoreType.setName(scoreType.getName());
		existingScoreType.setCoefficient(scoreType.getCoefficient());
		return scoreTypeRepository.save(existingScoreType);
	}

	@Override
	public boolean deleteScoreType(Long id) {
		if (!scoreTypeRepository.existsById(id)) {
			throw new ScoreTypeNotFoundException("Không tìm thấy loại điểm có id: " + id);
		}
		scoreTypeRepository.deleteById(id);
		return true;
	}

	public class ScoreTypeNotFoundException extends RuntimeException {
		public ScoreTypeNotFoundException(String message) {
			super(message);
		}
	}

}