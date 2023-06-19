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
import com.school.management.model.Teacher;
import com.school.management.service.TeacherService;
import com.school.management.service.TeacherServiceImpl;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/create")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDto teacherDto) {
        try {
            teacherService.createTeacher(teacherDto);
            return ResponseEntity.ok().body(teacherDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id,
            @RequestBody TeacherDto teacher) {
        try {
            Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
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
    public ResponseEntity<List<TeacherDto>> getAllTeacher() {
        List<TeacherDto> teacher = teacherService.getAllTeacher();
        return ResponseEntity.ok(teacher);
    }

    // @GetMapping("/findById/{email}")
    // public ResponseEntity<?> getTeacherById(@PathVariable String email) {
    //     try {
    //         TeacherDto teacher = teacherService.getTeacherByEmail(email);
    //         return ResponseEntity.ok(teacher);
    //     } catch (TeacherServiceImpl.TeacherNotFoundException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<TeacherDto>> getTeacherByName(@PathVariable(value = "name") String name) {
        List<TeacherDto> teacher = teacherService.getTeacherByName(name);
        return ResponseEntity.ok(teacher);
    }
}
