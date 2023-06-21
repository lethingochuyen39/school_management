package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.Classes;
import com.school.management.repository.ClassesRepository;

@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    private ClassesRepository classesRepository;

    @Override
    public Classes createClasses(Classes classes) {
        if (classes.getName() == null || classes.getDescription() == null
                || classes.getAcademicYear() == null || classes.getTeacher() == null
                || classes.getGrade() == null) {
            throw new IllegalArgumentException("Name, description, value, and year are required.");
        }

        return classesRepository.save(classes);
    }

    @Override
    public Classes getClassesById(Long id) {
        return classesRepository.findById(id)
				.orElseThrow(() -> new ClassesNotFoundException("Metric not found with id: " + id));
    }

    @Override
    public Classes updateClasses(Long id, Classes classes) {
        if (!classesRepository.existsById(id)) {
			throw new ClassesNotFoundException("Metric not found with id: " + id);
		}

		if (classes.getName() == null || classes.getDescription() == null
                || classes.getAcademicYear() == null || classes.getTeacher() == null
                || classes.getGrade() == null) {
			throw new IllegalArgumentException("Name, description, value, and year are required.");
		}

		// Classes existingClasses = classesRepository.findById(id)
		// 		.orElseThrow(() -> new ClassesNotFoundException("Metric not found with id: " + id));

		// if (existingClasses != null && !existingClasses.getName().equals(classes.getName()) && !existingClasses.getName().equals(classes.getTeacher())) {
		// 	if (classesRepository.existsByName(classes.getName())) {
		// 		throw new IllegalArgumentException("Academic year with the same name already exists.");
		// 	}
		// }
		classes.setId(id);
		return classesRepository.save(classes);
    }

    @Override
    public boolean deleteClasses(Long id) {
        if (!classesRepository.existsById(id)) {
			throw new ClassesNotFoundException("Metric not found with id: " + id);
		}
		classesRepository.deleteById(id);
		return true;
    }

    @Override
    public List<Classes> getAllClasses() {
        return classesRepository.findAll();
    }

    @Override
    public List<Classes> getClassesByName(String name) {
        return classesRepository.findByNameContainingIgnoreCase(name);
    }

    public class ClassesNotFoundException extends RuntimeException {
        public ClassesNotFoundException(String message) {
            super(message);
        }
    }
}
