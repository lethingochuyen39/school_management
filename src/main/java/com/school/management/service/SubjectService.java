package com.school.management.service;

import java.util.List;

import com.school.management.model.Subject;

public interface SubjectService {

    Subject createSubject(Subject subject);

    Subject updateSubject(Long id, Subject subject);

    boolean deleteSubject(Long id);

    List<Subject> getAllSubject();

    // SubjectDto getSubjectById(Long id);

    List<Subject> getSubjectByName(String name);
}
