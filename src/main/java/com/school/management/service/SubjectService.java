package com.school.management.service;

import java.util.List;

import com.school.management.dto.SubjectDto;
import com.school.management.model.Subject;

public interface SubjectService {

    Subject createSubject(SubjectDto subjectDto);

    Subject updateSubject(Long id, SubjectDto subjectDto);

    boolean deleteSubject(Long id);

    List<Subject> getAllSubject();

    Subject getSubjectById(Long id);

    List<Subject> getSubjectByName(String name);

    void addTeacherToSubject(Long subjectId, Long teacherId);

    void deleteTeacherFromSubject(Long subjectId, Long teacherId);

}
