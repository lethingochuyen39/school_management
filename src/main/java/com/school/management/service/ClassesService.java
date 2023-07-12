package com.school.management.service;

import java.util.List;

import com.school.management.dto.ClassesDto;
import com.school.management.model.Classes;

public interface ClassesService {
    Classes createClasses(ClassesDto classesDto);

    Classes getClassesById(Long id);

    Classes updateClasses(Long id, ClassesDto classesDto);

    boolean deleteClasses(Long id);

    List<Classes> getAllClasses();

    List<Classes> getClassesByName(String name);

    // huyen
    List<Classes> getClassesByTeacherId(Long teacherId);
}
