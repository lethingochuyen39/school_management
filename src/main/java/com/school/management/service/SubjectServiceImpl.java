package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.SubjectDto;
import com.school.management.model.Subject;
import com.school.management.model.Teacher;
import com.school.management.repository.SubjectRepository;
import com.school.management.repository.TeacherRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Subject createSubject(SubjectDto subjectDto) {
        Long teacherId = subjectDto.getTeacherId();

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + teacherId));

        Subject subject = new Subject();
        subject.setName(subjectDto.getName());
        subject.setTeacher(teacher);

        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Long id, SubjectDto subjectDto) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + id));

        Long teachertId = subjectDto.getTeacherId();

        Teacher teacher = teacherRepository.findById(teachertId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + teachertId));

        existingSubject.setTeacher(teacher);
        existingSubject.setName(subjectDto.getName());

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
    public List<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));

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