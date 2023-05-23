package com.school.management.security.service;

import java.util.List;

import com.school.management.model.AcademicYear;

public interface AcademicYearService {

	AcademicYear createAcademicYear(AcademicYear academicYear);

	AcademicYear getAcademicYearById(Long id);

	AcademicYear updateAcademicYear(Long id, AcademicYear academicYear);

	boolean deleteAcademicYear(Long id);

	List<AcademicYear> getAllAcademicYears();

	List<AcademicYear> getAcademicYearsByName(String name);

}
