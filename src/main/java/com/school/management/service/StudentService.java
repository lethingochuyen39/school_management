package com.school.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.school.management.dto.StudentDTO;
@Service
public interface StudentService {
    StudentDTO GetStudent(String email);
    StudentDTO UpdateProfile(StudentDTO student);
    List<StudentDTO> GetAllStudent();
    String DeleteStudent(String email);
    StudentDTO AddStudent(StudentDTO student);
}
