package com.school.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.ClassesDto;
import com.school.management.model.Classes;
import com.school.management.repository.ClassesRepository;

@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    private ClassesRepository classesRepository;

    @Override
    public Classes getClassesById(Long id) {
        return classesRepository.findById(id)
                .orElseThrow(() -> new ClassesNotFoundException("Class not found with id: " + id));

    }

    @Override
    public Classes createClasses(ClassesDto classesDto) {
        if (classesDto.getName() == null || classesDto.getDescription() == null
                || classesDto.getAcademicYear() == null || classesDto.getTeacher() == null
                || classesDto.getGrade() == null) {
            throw new IllegalArgumentException("Name, Description, Academic Year, Teacher, and Grade are required.");
        }

        if (classesRepository.existsByName(classesDto.getName())) {
            throw new IllegalArgumentException("Class already exists.");
        }

        Classes classes = new Classes();
        classes.setName(classesDto.getName());
        classes.setDescription(classesDto.getDescription());
        classes.setAcademicYear(classesDto.getAcademicYear());
        classes.setTeacher(classesDto.getTeacher());
        classes.setGrade(classesDto.getGrade());

        return classesRepository.save(classes);
    }

    @Override
    public Classes updateClasses(Long id, ClassesDto classesDto) {
        if (!classesRepository.existsById(id)) {
            throw new ClassesNotFoundException("Class not found with id: " + id);
        }

        if (classesDto.getName() == null || classesDto.getDescription() == null
                || classesDto.getAcademicYear() == null || classesDto.getTeacher() == null
                || classesDto.getGrade() == null) {
            throw new IllegalArgumentException("Name, Description, Academic Year, Teacher, and Grade are required.");
        }

        Classes existingClasses = classesRepository.findById(id)
                .orElseThrow(() -> new ClassesNotFoundException("Class not found with id: " + id));

        if (!existingClasses.getName().equals(classesDto.getName())) {
            if (classesRepository.existsByName(classesDto.getName())) {
                throw new IllegalArgumentException("Class already exists.");
            }
        }

        existingClasses.setName(classesDto.getName());
        existingClasses.setDescription(classesDto.getDescription());
        existingClasses.setAcademicYear(classesDto.getAcademicYear());
        existingClasses.setTeacher(classesDto.getTeacher());
        existingClasses.setGrade(classesDto.getGrade());

        return classesRepository.save(existingClasses);
    }

    @Override
    public boolean deleteClasses(Long id) {
        if (!classesRepository.existsById(id)) {
            throw new ClassesNotFoundException("Class not found with id: " + id);
        }
        classesRepository.deleteById(id);
        return true;
    }

    @Override
    public List<ClassesDto> getAllClasses() {
        List<Classes> list = classesRepository.findAll();
        List<ClassesDto> listDto = new ArrayList<>();
        for (var i : list) {
            ClassesDto dto = new ClassesDto();
            dto.setDescription(i.getDescription()).setName(i.getName()).setTeacher(i.getTeacher())
                    .setAcademicYear(i.getAcademicYear()).setGrade(i.getGrade()).setId(i.getId());
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public List<ClassesDto> getClassesByName(String name) {
        List<Classes> list = classesRepository.findByNameContainingIgnoreCase(name);
        List<ClassesDto> listDto = new ArrayList<>();
        for (var i : list) {
            ClassesDto dto = new ClassesDto();
            dto.setDescription(i.getDescription()).setName(i.getName()).setTeacher(i.getTeacher())
                    .setAcademicYear(i.getAcademicYear()).setGrade(i.getGrade());
            listDto.add(dto);
        }
        return listDto;
    }

    public class ClassesNotFoundException extends RuntimeException {
        public ClassesNotFoundException(String message) {
            super(message);
        }
    }
}
