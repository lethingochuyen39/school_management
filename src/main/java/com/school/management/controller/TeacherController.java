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

import com.school.management.model.Teacher;
import com.school.management.service.TeacherServiceImpl;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    private TeacherServiceImpl teacherServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createTeacher(@RequestBody Teacher teacher) {
        try {
            Teacher createdTeacher = teacherServiceImpl.createTeacher(teacher);
            return ResponseEntity.ok().body(createdTeacher);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id,
            @RequestBody Teacher teacher) {
        try {
            Teacher updatedTeacher = teacherServiceImpl.updateTeacher(id, teacher);
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
            teacherServiceImpl.deleteTeacher(id);
            return ResponseEntity.ok().build();
        } catch (TeacherServiceImpl.TeacherNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeacher() {
        List<Teacher> teacher = teacherServiceImpl.getAllTeacher();
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        try {
            Teacher teacher = teacherServiceImpl.getTeacherById(id);
            return ResponseEntity.ok(teacher);
        } catch (TeacherServiceImpl.TeacherNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<Teacher>> getTeacherByName(@PathVariable(value = "name") String name) {
        List<Teacher> teacher = teacherServiceImpl.getTeacherByName(name);
        return ResponseEntity.ok(teacher);
    }
}
