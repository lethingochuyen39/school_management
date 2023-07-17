package com.school.management.service;

import java.util.List;
import java.util.Set;

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
        Subject subject = new Subject();
        subject.setName(subjectDto.getName());

        Set<Teacher> teachers = subjectDto.getTeachers();
        if (teachers != null && !teachers.isEmpty()) {
            for (Teacher teacher : teachers) {
                Teacher existingTeacher = teacherRepository.findById(teacher.getId())
                        .orElseThrow(
                                () -> new SubjectNotFoundException("Teacher not found with id: " + teacher.getId()));
                subject.getTeachers().add(existingTeacher);
            }
        }

        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Long id, SubjectDto subjectDto) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));

        existingSubject.setName(subjectDto.getName());

        Set<Teacher> teachers = subjectDto.getTeachers();
        if (teachers != null && !teachers.isEmpty()) {
            existingSubject.getTeachers().clear();
            for (Teacher teacher : teachers) {
                Teacher existingTeacher = teacherRepository.findById(teacher.getId())
                        .orElseThrow(
                                () -> new SubjectNotFoundException("Teacher not found with id: " + teacher.getId()));
                existingSubject.getTeachers().add(existingTeacher);
            }
        }

        return subjectRepository.save(existingSubject);
    }

    @Override
    public boolean deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));

        subjectRepository.delete(subject);
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

    // @Override
    // public List<Subject> getSubjectsByTeacherId(Long teacherId) {
    // return subjectRepository.findByTeacherId(teacherId);
    // }

    public class SubjectNotFoundException extends RuntimeException {
        public SubjectNotFoundException(String message) {
            super(message);
        }
    }
}
