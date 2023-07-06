package com.school.management.service;

import java.util.List;

import com.school.management.dto.TeacherDto;
import com.school.management.model.Teacher;

public interface TeacherService {
    
    //Teacher createTeacher(Teacher teacher, MultipartFile file, Long uploadedById);
    Teacher createTeacher(TeacherDto teacherDto);

    //Teacher updateTeacher(Long id, Teacher teacher, MultipartFile file);
    Teacher updateTeacher(Long id, TeacherDto teacherDto);

    boolean deleteTeacher(Long id);

    Teacher getTeacherById(Long id);

    List<Teacher> getAllTeacher();

    // TeacherDto getTeacherByEmail(String email);

    List<Teacher> getTeacherByName(String name);

}
