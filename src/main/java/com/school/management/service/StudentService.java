package com.school.management.service;

import java.util.List;

import com.school.management.dto.StudentDTO;
public interface StudentService {
    StudentDTO GetStudent(String email);
    StudentDTO UpdateProfile(StudentDTO student);
    List<StudentDTO> GetAllStudent();
    String DeleteStudent(String email);
    StudentDTO AddStudent(StudentDTO student);
    // Student GiveAccessAccount(String email,Student student);
    Long generateAccount();
}