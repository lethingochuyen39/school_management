package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.dto.TeacherDto;
import com.school.management.model.Classes;
import com.school.management.model.Teacher;
import com.school.management.service.AcademicYearServiceImpl;
import com.school.management.service.ClassesServiceImpl;
import com.school.management.service.EventNewsServiceImpl.EventNewsNotFoundException;
import com.school.management.service.TeacherService;
import com.school.management.service.TeacherServiceImpl;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    // huyen
    @Autowired
    private ClassesServiceImpl classesServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDto teacherDto) {
        try {
            Teacher createdTeacher = teacherService.createTeacher(teacherDto);
            return ResponseEntity.ok().body(createdTeacher);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id,
            @RequestBody TeacherDto teacherDto) {
        try {
            Teacher updatedTeacher = teacherService.updateTeacher(id, teacherDto);
            return ResponseEntity.ok(updatedTeacher);
        } catch (TeacherServiceImpl.TeacherNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok().build();
        } catch (TeacherServiceImpl.TeacherNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeacher() {
        List<Teacher> teacher = teacherService.getAllTeacher();
        return ResponseEntity.ok(teacher);
    }

    // @GetMapping("/findById/{email}")
    // public ResponseEntity<?> getTeacherById(@PathVariable String email) {
    // try {
    // TeacherDto teacher = teacherService.getTeacherByEmail(email);
    // return ResponseEntity.ok(teacher);
    // } catch (TeacherServiceImpl.TeacherNotFoundException e) {
    // return ResponseEntity.badRequest().body(e.getMessage());
    // }
    // }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<Teacher>> getTeacherByName(@PathVariable(value = "name") String name) {
        List<Teacher> teacher = teacherService.getTeacherByName(name);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        try {
            Teacher teacher = teacherService.getTeacherById(id);
            return ResponseEntity.ok(teacher);
        } catch (AcademicYearServiceImpl.AcademicYearNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // huyen
    @GetMapping("/{teacherId}/classes")
    public ResponseEntity<List<?>> getClassesByTeacherId(@PathVariable Long teacherId) {
        List<Classes> classes = classesServiceImpl.getClassesByTeacherId(teacherId);
        return ResponseEntity.ok(classes);
    }

    @PutMapping("/isActive/{id}")
    public ResponseEntity<?> updateIsActive(@PathVariable("id") Long id) {
        try {
            Teacher updatedTeacher = teacherService.updateTeacherStatus(id);
            return ResponseEntity.ok(updatedTeacher);
        } catch (EventNewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
