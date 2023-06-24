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
import org.springframework.web.bind.annotation.RestController;

import com.school.management.dto.ParentDTO;
import com.school.management.service.ParentService;

@RestController
public class ParentController {
    @Autowired
    private ParentService ParentService;
    
    @GetMapping("/allParent")
    public ResponseEntity<List<ParentDTO>> getAllParent() {
        return ResponseEntity.ok(ParentService.GetAllParent());
    }

    @GetMapping("/Parent")
    public ResponseEntity<?> getParent(@RequestBody String email) {
        try {
            return ResponseEntity.ok(ParentService.GetParent(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createParent")
    public ResponseEntity<?> addParent(@RequestBody ParentDTO entity) {
        try {
            return ResponseEntity.ok(ParentService.AddParent(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteParent")
    public ResponseEntity<?> delete(@RequestBody String email) {
        try {
            return ResponseEntity.ok(ParentService.DeleteParent(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateParent/{id}")
    public ResponseEntity<?> updateParent(@PathVariable String id, @RequestBody ParentDTO entity) {
        try {
            return ResponseEntity.ok(ParentService.UpdateProfile(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/giveAccessAccount")
    public ResponseEntity<?> giveAccessAccount() {
        try {
            return ResponseEntity.ok(ParentService.generateAccount());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
