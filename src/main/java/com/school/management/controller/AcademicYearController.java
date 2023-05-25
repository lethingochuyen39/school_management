package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.model.AcademicYear;
import com.school.management.payload.response.MessageResponse;
import com.school.management.service.AcademicYearServiceImpl;
import com.school.management.service.AcademicYearServiceImpl.AcademicYearNotFoundException;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {

	@Autowired
	private AcademicYearServiceImpl academicYearServiceImpl;

	@PostMapping
	public ResponseEntity<?> createAcademicYear(@RequestBody AcademicYear academicYear) {
		try {
			AcademicYear createdAcademicYear = academicYearServiceImpl.createAcademicYear(academicYear);
			return ResponseEntity.ok().body(createdAcademicYear);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); // Thông báo lỗi nếu không thể tạo AcademicYear
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAcademicYearById(@PathVariable Long id) {
		try {
			AcademicYear academicYear = academicYearServiceImpl.getAcademicYearById(id);
			return ResponseEntity.ok(academicYear);
		} catch (AcademicYearServiceImpl.AcademicYearNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateAcademicYear(@PathVariable Long id,
			@RequestBody AcademicYear academicYear) {
		try {
			AcademicYear updatedAcademicYear = academicYearServiceImpl.updateAcademicYear(id, academicYear);
			return ResponseEntity.ok(updatedAcademicYear);
		} catch (AcademicYearServiceImpl.AcademicYearNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); // Thông báo lỗi nếu không thể cập nhật //
																		// AcademicYear
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAcademicYear(@PathVariable Long id) {
		try {
			academicYearServiceImpl.deleteAcademicYear(id);
			return ResponseEntity.ok().build();
		} catch (AcademicYearServiceImpl.AcademicYearNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<AcademicYear>> getAllAcademicYears() {
		List<AcademicYear> academicYears = academicYearServiceImpl.getAllAcademicYears();
		return ResponseEntity.ok(academicYears);
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<AcademicYear>> searchAcademicYearsByName(@PathVariable(value = "name") String name) {
		List<AcademicYear> academicYears = academicYearServiceImpl.getAcademicYearsByName(name);
		return ResponseEntity.ok(academicYears);
	}
}
