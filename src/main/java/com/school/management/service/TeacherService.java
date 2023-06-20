package com.school.management.service;

import java.util.List;

import com.school.management.model.Teacher;

public interface TeacherService {
    
    Teacher createTeacher(Teacher teacher);

    Teacher updateTeacher(Long id, Teacher teacher);

    boolean deleteTeacher(Long id);

    List<Teacher> getAllTeacher();

    // TeacherDto getTeacherByEmail(String email);

    List<Teacher> getTeacherByName(String name);

}
