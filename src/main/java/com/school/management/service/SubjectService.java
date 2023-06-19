package com.school.management.service;

import java.util.List;

import com.school.management.dto.SubjectDto;
import com.school.management.model.Subject;

public interface SubjectService {

    Subject createSubject(SubjectDto subject);

    Subject updateSubject(Long id, SubjectDto subject);

    boolean deleteSubject(Long id);

    List<SubjectDto> getAllSubject();

    // SubjectDto getSubjectById(Long id);

    List<SubjectDto> getSubjectByName(String name);
}
