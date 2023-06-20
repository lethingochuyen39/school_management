package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.Teacher;
import com.school.management.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher createTeacher(Teacher teacher) {
        if (teacher.getName() == null || teacher.getGender() == null ||
                teacher.getDob() == null
                || teacher.getEmail() == null || teacher.getAddress() == null ||
                teacher.getPhone() == null
                || teacher.getStatus() == null || teacher.getImage() == null) {
            throw new IllegalArgumentException("Name, description, value, and year are required.");
        }

        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacher) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Metric not found with id: " + id);
        }

        if (teacher.getName() == null || teacher.getGender() == null ||
                teacher.getDob() == null
                || teacher.getEmail() == null || teacher.getAddress() == null ||
                teacher.getPhone() == null
                || teacher.getStatus() == null || teacher.getImage() == null) {
            throw new IllegalArgumentException("Name, description, value, and year are required.");
        }

        teacher.setId(id);
        return teacherRepository.save(teacher);
    }

    @Override
    public boolean deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Metric not found with id: " + id);
        }
        teacherRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Teacher> getTeacherByName(String name) {
        return teacherRepository.findByNameContainingIgnoreCase(name);
    }

    public class TeacherNotFoundException extends RuntimeException {
        public TeacherNotFoundException(String message) {
            super(message);
        }
    }
}
