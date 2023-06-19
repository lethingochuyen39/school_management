package com.school.management.service;

import java.util.List;

import com.school.management.dto.TeacherDto;
import com.school.management.model.Teacher;

public interface TeacherService {
    
    Teacher createTeacher(TeacherDto teacher);

    Teacher updateTeacher(Long id, TeacherDto teacher);

    boolean deleteTeacher(Long id);

    List<TeacherDto> getAllTeacher();

    // TeacherDto getTeacherByEmail(String email);

    List<TeacherDto> getTeacherByName(String name);

}
