package com.school.management.service;

import com.school.management.dto.ScoreDTO;
import com.school.management.model.Score;
import com.school.management.model.ScoreType;
import com.school.management.model.Student;
import com.school.management.model.Subject;
import com.school.management.repository.ScoreRepository;
import com.school.management.repository.ScoreTypeRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

	@Autowired
	private ScoreRepository scoreRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private ScoreTypeRepository scoreTypeRepository;

	@Override
	public List<Score> getAllScores() {
		return scoreRepository.findAll();
	}

	@Override
	public Score getScoreById(Long id) {
		return scoreRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Score not found with id: " + id));
	}

	@Override
	public Score createScore(ScoreDTO scoreDTO) {
		Long studentId = scoreDTO.getStudentId();
		Long subjectId = scoreDTO.getSubjectId();
		Long scoreTypeId = scoreDTO.getScoreTypeId();

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + subjectId));
		ScoreType scoreType = scoreTypeRepository.findById(scoreTypeId)
				.orElseThrow(() -> new IllegalArgumentException("ScoreType not found with id: " + scoreTypeId));

		if (scoreRepository.existsByStudentIdAndSubjectIdAndScoreTypeId(studentId, subjectId, scoreTypeId)) {
			throw new IllegalArgumentException("Score with the same Student, Subject, and ScoreType already exists");
		}

		Score score = new Score();
		score.setStudent(student);
		score.setSubject(subject);
		score.setScoreType(scoreType);
		score.setScore(scoreDTO.getScore());

		return scoreRepository.save(score);
	}

	@Override
	public Score updateScore(Long id, ScoreDTO scoreDTO) {
		Score existingScore = scoreRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Score not found with id: " + id));

		Long studentId = scoreDTO.getStudentId();
		Long subjectId = scoreDTO.getSubjectId();
		Long scoreTypeId = scoreDTO.getScoreTypeId();

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + subjectId));
		ScoreType scoreType = scoreTypeRepository.findById(scoreTypeId)
				.orElseThrow(() -> new IllegalArgumentException("ScoreType not found with id: " + scoreTypeId));

		if (scoreRepository.existsByStudentIdAndSubjectIdAndScoreTypeId(studentId, subjectId, scoreTypeId)) {
			throw new IllegalArgumentException("Score with the same Student, Subject, and ScoreType already exists");
		}

		if (scoreRepository.existsByStudentIdAndSubjectIdAndScoreTypeId(studentId, subjectId, scoreTypeId)
				&& !existingScore.getStudent().getId().equals(studentId)
				&& !existingScore.getSubject().getId().equals(subjectId)
				&& !existingScore.getScoreType().getId().equals(scoreTypeId)) {
			throw new IllegalArgumentException("Score with the same Student, Subject, and ScoreType already exists");
		}

		existingScore.setStudent(student);
		existingScore.setSubject(subject);
		existingScore.setScoreType(scoreType);
		existingScore.setScore(scoreDTO.getScore());

		return scoreRepository.save(existingScore);
	}

	@Override
	public void deleteScore(Long id) {
		if (!scoreRepository.existsById(id)) {
			throw new IllegalArgumentException("Score not found with id: " + id);
		}
		scoreRepository.deleteById(id);
	}

	@Override
	public List<Score> searchScoresByStudentName(String studentName) {
		return scoreRepository.findByStudentName(studentName);
	}

	@Override
	public List<Score> searchScoresByStudentId(Long studentId) {
		return scoreRepository.findByStudentId(studentId);
	}

	// @Override
	// public List<Score> searchScoresByClassName(String className) {
	// return scoreRepository.findByStudentClassName(className);
	// }
}