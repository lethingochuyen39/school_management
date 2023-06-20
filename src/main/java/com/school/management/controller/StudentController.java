package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.dto.StudentDTO;
import com.school.management.service.StudentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/allStudent")
    public ResponseEntity<List<StudentDTO>> getAllStudent() {
        return ResponseEntity.ok(studentService.GetAllStudent());
    }

    @GetMapping("/student")
    public ResponseEntity<?> getStudent(@RequestBody String email) {
        try {
            return ResponseEntity.ok(studentService.GetStudent(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createStudent")
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO entity) {
        try {
            return ResponseEntity.ok(studentService.AddStudent(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteStudent")
    public ResponseEntity<?> delete(@RequestBody String email) {
        try {
            return ResponseEntity.ok(studentService.DeleteStudent(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("updateStudent/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable String id, @RequestBody StudentDTO entity) {
        try {
            return ResponseEntity.ok(studentService.UpdateProfile(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
