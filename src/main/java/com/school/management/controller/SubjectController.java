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

import com.school.management.dto.SubjectDto;
import com.school.management.model.Subject;
import com.school.management.service.SubjectService;
import com.school.management.service.SubjectServiceImpl;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<?> createSubject(@RequestBody SubjectDto subject) {
        try {
            Subject createdSubject = subjectService.createSubject(subject);
            return ResponseEntity.ok().body(createdSubject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable Long id,
            @RequestBody SubjectDto subject) {
        try {
            Subject updatedSubject = subjectService.updateSubject(id, subject);
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
            subjectService.deleteSubject(id);
            return ResponseEntity.ok().build();
        } catch (SubjectServiceImpl.SubjectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubject() {
        List<SubjectDto> subject = subjectService.getAllSubject();
        return ResponseEntity.ok(subject);
    }

    // @GetMapping("/findById/{id}")
    // public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
    // try {
    // SubjectDto subject = subjectServiceImpl.getSubjectById(id);
    // return ResponseEntity.ok(subject);
    // } catch (SubjectServiceImpl.SubjectNotFoundException e) {
    // return ResponseEntity.badRequest().body(e.getMessage());
    // }
    // }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<SubjectDto>> getSubjectByName(@PathVariable(value = "name") String name) {
        List<SubjectDto> subject = subjectService.getSubjectByName(name);
        return ResponseEntity.ok(subject);
    }
}
