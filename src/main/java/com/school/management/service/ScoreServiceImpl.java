package com.school.management.service;

import com.school.management.dto.ScoreDTO;
import com.school.management.model.Classes;
import com.school.management.model.Score;
import com.school.management.model.ScoreType;
import com.school.management.model.Student;
import com.school.management.model.Subject;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.ScoreRepository;
import com.school.management.repository.ScoreTypeRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	@Autowired
	private ClassesRepository classesRepository;

	@Override
	public List<Score> getAllScores() {
		return scoreRepository.findAll();
	}

	@Override
	public Score getScoreById(Long id) {
		return scoreRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy điểm với id: " + id));
	}

	@Override
	public Score createScore(ScoreDTO scoreDTO) {
		Long studentId = scoreDTO.getStudentId();
		Long subjectId = scoreDTO.getSubjectId();
		Long scoreTypeId = scoreDTO.getScoreTypeId();
		Long classId = scoreDTO.getClassId();
		Integer semester = scoreDTO.getSemester();

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy học sinh với id: " + studentId));
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy môn học với id: " + subjectId));
		ScoreType scoreType = scoreTypeRepository.findById(scoreTypeId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại điểm với id: " + scoreTypeId));
		Classes classes = classesRepository.findById(classId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lớp học với id: " + classId));
		if (scoreRepository.findByStudentAndSubjectAndScoreTypeAndSemesterAndClasses(
				student, subject, scoreType, semester, classes) != null) {
			throw new IllegalArgumentException(
					"Điểm số đã tồn tại cho học sinh, môn học, lớp học và học kì tương ứng.");
		}
		Score score = new Score();
		score.setStudent(student);
		score.setSubject(subject);
		score.setScoreType(scoreType);
		score.setScore(scoreDTO.getScore());
		score.setSemester(semester);
		score.setClasses(classes);

		return scoreRepository.save(score);
	}

	@Override
	public Score updateScore(Long id, ScoreDTO scoreDTO) {
		Score existingScore = scoreRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy điểm với id: " + id));
		existingScore.setScore(scoreDTO.getScore());

		return scoreRepository.save(existingScore);
	}

	@Override
	public void deleteScore(Long id) {
		if (!scoreRepository.existsById(id)) {
			throw new IllegalArgumentException("Không tìm thấy điểm với id: " + id);
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

	@Override
	public List<Score> searchScore(String studentName) {
		if (studentName == null || studentName.trim().isEmpty()) {
			return getAllScores();
		} else {
			return searchScoresByStudentName(studentName);
		}
	}

	// @Override
	// public List<Score> findByClassId(Long classId) {
	// return scoreRepository.findByClassId(classId);
	// }

	@Override
	public List<Score> getScoresByClassAndSemesterAndStudentId(Long classId, Integer semester, Long studentId) {
		return scoreRepository.findByClassesIdAndSemesterAndStudentId(classId, semester, studentId);
	}

}