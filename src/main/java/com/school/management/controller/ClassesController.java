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

import com.school.management.model.Classes;
import com.school.management.service.ClassesServiceImpl;
import com.school.management.service.MetricServiceImpl;

@RestController
@RequestMapping("/api/classes")
public class ClassesController {
    @Autowired
    private ClassesServiceImpl classesServiceImpl;

    @PostMapping
    public ResponseEntity<?> createClasses(@RequestBody Classes classes) {
        try {
            Classes createdClasses = classesServiceImpl.createClasses(classes);
            return ResponseEntity.ok().body(createdClasses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
	public ResponseEntity<?> getClassesById(@PathVariable Long id) {
		try {
			Classes metric = classesServiceImpl.getClassesById(id);
			return ResponseEntity.ok(metric);
		} catch (MetricServiceImpl.MetricNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateClasses(@PathVariable Long id,
			@RequestBody Classes classes) {
		try {
			Classes updatedClasses = classesServiceImpl.updateClasses(id, classes);
			return ResponseEntity.ok(updatedClasses);
		} catch (ClassesServiceImpl.ClassesNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteClasses(@PathVariable Long id) {
		try {
			classesServiceImpl.deleteClasses(id);
			return ResponseEntity.ok().build();
		} catch (ClassesServiceImpl.ClassesNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Classes>> getAllClasses() {
		List<Classes> classes = classesServiceImpl.getAllClasses();
		return ResponseEntity.ok(classes);
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<Classes>> searchClassesByName(@PathVariable(value = "name") String name) {
		List<Classes> classes = classesServiceImpl.getClassesByName(name);
		return ResponseEntity.ok(classes);
	}
}
