package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.AcademicYear;

public interface AcademicYearRespository extends JpaRepository<AcademicYear, Long> {

	boolean existsByName(String name);

	List<AcademicYear> findByNameContainingIgnoreCase(String name);
}
