package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.Subject;
import com.school.management.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject createSubject(Subject subject) {
        if (subject.getName() == null || subject.getTeacher() == null) {
            throw new IllegalArgumentException("Lack of information.");
        }

        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Long id, Subject subject) {
        if (!subjectRepository.existsById(id)) {
            throw new SubjectNotFoundException("Metric not found with id: " + id);
        }

        if (subject.getName() == null || subject.getTeacher() == null) {
            throw new IllegalArgumentException("Name, description, value, and year are required.");
        }

        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Metric not found with id: " + id));

        if (existingSubject != null && !existingSubject.getName().equals(subject.getName())) {
            if (subjectRepository.existsByName(subject.getName())) {
                throw new IllegalArgumentException("Academic year with the same name already exists.");
            }
        }
        subject.setId(id);
        return subjectRepository.save(subject);
    }

    @Override
    public boolean deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new SubjectNotFoundException("Metric not found with id: " + id);
        }
        subjectRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }

    @Override
    public List<Subject> getSubjectByName(String name) {
        return subjectRepository.findByNameContainingIgnoreCase(name);
    }

    public class SubjectNotFoundException extends RuntimeException {
        public SubjectNotFoundException(String message) {
            super(message);
        }
    }

    // huyen
    @Override
    public List<Subject> getSubjectsByTeacherId(Long teacherId) {
        return subjectRepository.findByTeacherId(teacherId);
    }
}