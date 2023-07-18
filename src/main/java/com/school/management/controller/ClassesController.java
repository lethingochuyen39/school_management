package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.dto.ClassesDto;
import com.school.management.model.Classes;
import com.school.management.service.ClassesService;
import com.school.management.service.ClassesServiceImpl;

@RestController
@RequestMapping("/api/classes")
public class ClassesController {
	@Autowired
	private ClassesService classesService;

    @PostMapping("/create")
    public ResponseEntity<?> createClasses(@RequestBody ClassesDto classesDto) {
        try {
            Classes createdClasses = classesService.createClasses(classesDto);
            return ResponseEntity.ok().body(createdClasses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

	@GetMapping("/findById/{id}")
	public ResponseEntity<?> getClassesById(@PathVariable Long id) {
		try {
			Classes classes = classesService.getClassesById(id);
			return ResponseEntity.ok(classes);
		} catch (ClassesServiceImpl.ClassesNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateClasses(@PathVariable Long id,
			@RequestBody ClassesDto classesDto) {
		try {
			Classes updatedClasses = classesService.updateClasses(id, classesDto);
			return ResponseEntity.ok(updatedClasses);
		} catch (ClassesServiceImpl.ClassesNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteClasses(@PathVariable Long id) {
		try {
			classesService.deleteClasses(id);
			return ResponseEntity.ok().build();
		} catch (ClassesServiceImpl.ClassesNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Classes>> getAllClasses() {
		List<Classes> classes = classesService.getAllClasses();
		return ResponseEntity.ok(classes);
	}

	@GetMapping("/findByName/{name}")
	public ResponseEntity<List<Classes>> searchClassesByName(@PathVariable(value = "name") String name) {
		List<Classes> classes = classesService.getClassesByName(name);
		return ResponseEntity.ok(classes);
	}

	// huyen
	@GetMapping("/teachers/{teacherId}")
	public List<Classes> getClassesByTeacherId(@PathVariable Long teacherId) {
		return classesService.getClassesByTeacherId(teacherId);
	}
}
