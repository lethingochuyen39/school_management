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

import com.school.management.model.Subject;
import com.school.management.service.SubjectServiceImpl;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectServiceImpl subjectServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createSubject(@RequestBody Subject subject) {
        try {
            Subject createdSubject = subjectServiceImpl.createSubject(subject);
            return ResponseEntity.ok().body(createdSubject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable Long id,
            @RequestBody Subject subject) {
        try {
            Subject updatedSubject = subjectServiceImpl.updateSubject(id, subject);
            return ResponseEntity.ok(updatedSubject);
        } catch (SubjectServiceImpl.SubjectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        try {
            subjectServiceImpl.deleteSubject(id);
            return ResponseEntity.ok().build();
        } catch (SubjectServiceImpl.SubjectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubject() {
        List<Subject> subject = subjectServiceImpl.getAllSubject();
        return ResponseEntity.ok(subject);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        try {
            Subject subject = subjectServiceImpl.getSubjectById(id);
            return ResponseEntity.ok(subject);
        } catch (SubjectServiceImpl.SubjectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<Subject>> getSubjectByName(@PathVariable(value = "name") String name) {
        List<Subject> subject = subjectServiceImpl.getSubjectByName(name);
        return ResponseEntity.ok(subject);
    }
}
