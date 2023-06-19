package com.school.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.SubjectDto;
import com.school.management.model.Subject;
import com.school.management.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject createSubject(SubjectDto subject) {
        if (subject.getName() == null || subject.getTeacher() == null) {
            throw new IllegalArgumentException("Subject and Teacher are required.");
        } else if (subjectRepository.existsByName(subject.getName())
                && subjectRepository.existsByTeacher(subject.getTeacher())) {
            throw new IllegalArgumentException("Subject and Teacher are already exists.");
        } else {
            Subject subject2 = new Subject(null, subject.getName(), subject.getTeacher());
            return subjectRepository.save(subject2);
        }
    }

    @Override
    public Subject updateSubject(Long id, SubjectDto subject) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty()) {
            throw new SubjectNotFoundException("Subject not found with id: " + id);
        }
        Subject existingSubject = optionalSubject.get();

        if (subject.getName() == null || subject.getTeacher() == null) {
            throw new IllegalArgumentException("Subject and Teacher are required.");
        }

        existingSubject.setName(subject.getName());
        existingSubject.setTeacher(subject.getTeacher());

        return subjectRepository.save(existingSubject);
    }

    @Override
    public boolean deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new SubjectNotFoundException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
        return true;
    }

    @Override
    public List<SubjectDto> getAllSubject() {
        List<Subject> list = subjectRepository.findAll();
        List<SubjectDto> listDto = new ArrayList<>();
        for (var i : list) {
            SubjectDto dto = new SubjectDto();
            dto.setName(i.getName()).setTeacher(i.getTeacher());
            listDto.add(dto);
        }
        return listDto;
    }

    // @Override
    // public SubjectDto getSubjectById(Long id) {
    // return subjectRepository.findById(id)
    // .orElseThrow(() -> new SubjectNotFoundException("Class not found with id: " +
    // id));
    // }

    @Override
    public List<SubjectDto> getSubjectByName(String name) {
        List<Subject> list = subjectRepository.findByNameContainingIgnoreCase(name);
        List<SubjectDto> listDto = new ArrayList<>();
        for (var i : list) {
            SubjectDto dto = new SubjectDto();
            dto.setName(i.getName()).setTeacher(i.getTeacher());
            listDto.add(dto);
        }
        return listDto;
    }

    public class SubjectNotFoundException extends RuntimeException {
        public SubjectNotFoundException(String message) {
            super(message);
        }
    }
}
