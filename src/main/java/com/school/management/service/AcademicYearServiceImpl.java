package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.AcademicYear;
import com.school.management.repository.AcademicYearRespository;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

	@Autowired
	private AcademicYearRespository academicYearRepository;

	@Override
	public AcademicYear createAcademicYear(AcademicYear academicYear) {
		if (academicYear.getName() == null || academicYear.getStartDate() == null
				|| academicYear.getEndDate() == null) {
			throw new IllegalArgumentException("Name, start date, and end date are required.");
		}

		if (academicYearRepository.existsByName(academicYear.getName())) {
			throw new IllegalArgumentException("Academic year with the same name already exists.");
		}

		return academicYearRepository.save(academicYear);
	}

	@Override
	public AcademicYear getAcademicYearById(Long id) {
		return academicYearRepository.findById(id)
				.orElseThrow(() -> new AcademicYearNotFoundException("Academic year not found with id: " + id));

	}

	@Override
	public AcademicYear updateAcademicYear(Long id, AcademicYear academicYear) {

		if (!academicYearRepository.existsById(id)) {
			throw new AcademicYearNotFoundException("Academic year not found with id: " + id);
		}

		if (academicYear.getName() == null || academicYear.getStartDate() == null
				|| academicYear.getEndDate() == null) {
			throw new IllegalArgumentException("Name, start date, and end date are required.");
		}

		AcademicYear existingAcademicYear = academicYearRepository.findById(id)
				.orElseThrow(() -> new AcademicYearNotFoundException("Academic year not found with id: " + id));

		if (existingAcademicYear != null && !existingAcademicYear.getName().equals(academicYear.getName())) {
			if (academicYearRepository.existsByName(academicYear.getName())) {
				throw new IllegalArgumentException("Academic year with the same name already exists.");
			}
		}
		academicYear.setId(id);
		return academicYearRepository.save(academicYear);
	}

	@Override
	public boolean deleteAcademicYear(Long id) {
		if (!academicYearRepository.existsById(id)) {
			throw new AcademicYearNotFoundException("Academic year not found with id: " + id);
		}
		academicYearRepository.deleteById(id);
		return true;
	}

	@Override
	public List<AcademicYear> getAllAcademicYears() {
		return academicYearRepository.findAll();
	}

	@Override
	public List<AcademicYear> getAcademicYearsByName(String name) {
		return academicYearRepository.findByNameContainingIgnoreCase(name);
	}

	public class AcademicYearNotFoundException extends RuntimeException {
		public AcademicYearNotFoundException(String message) {
			super(message);
		}
	}

}
