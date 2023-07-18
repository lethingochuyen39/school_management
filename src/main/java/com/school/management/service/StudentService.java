package com.school.management.service;

import java.util.List;

import com.school.management.dto.StudentDTO;
import com.school.management.model.Classes;

public interface StudentService {
    StudentDTO GetStudent(String email);

    StudentDTO UpdateProfile(StudentDTO student);

    List<StudentDTO> GetAllStudent();

    String DeleteStudent(String email);

    StudentDTO AddStudent(StudentDTO student);

    // Student GiveAccessAccount(String email,Student student);
    // Long generateAccount();
    String ConfirmStudent(StudentDTO studentDTO);
    String upgradeClass(String className,String email);

    Classes findClassByStudentId(Long studentId);

    List<Classes> findAllClassByStudentId(Long studentId);
}
