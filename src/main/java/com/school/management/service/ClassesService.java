package com.school.management.service;

import java.util.List;

import com.school.management.model.Classes;

public interface ClassesService {
    Classes createClasses(Classes classes);

    Classes getClassesById(Long id);

    Classes updateClasses(Long id, Classes classes);

    boolean deleteClasses(Long id);

    List<Classes> getAllClasses();

    List<Classes> getClassesByName(String name);

    // huyen
    List<Classes> getClassesByTeacherId(Long teacherId);
}
