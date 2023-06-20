package com.school.management.service;

import java.util.List;

import com.school.management.dto.ClassesDto;
import com.school.management.model.Classes;

public interface ClassesService {
    Classes createClasses(ClassesDto classes);

    Classes getClassesById(Long id);

    Classes updateClasses(Long id, ClassesDto classes);

    boolean deleteClasses(Long id);

    List<ClassesDto> getAllClasses();

    List<ClassesDto> getClassesByName(String name);
}
