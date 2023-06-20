package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.school.management.model.Attendance;
import com.school.management.service.AttendanceServiceImpl;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;

    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        Attendance createdAttendance = attendanceServiceImpl.createAttendance(attendance);
        return ResponseEntity.ok(createdAttendance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable("id") Long id) {
        Attendance attendance = attendanceServiceImpl.getAttendanceById(id);
        if (attendance != null) {
            return ResponseEntity.ok(attendance);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable("id") Long id, @RequestBody Attendance attendance) {
        Attendance updatedAttendance = attendanceServiceImpl.updateAttendance(id, attendance);
        if (updatedAttendance != null) {
            return ResponseEntity.ok(updatedAttendance);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceServiceImpl.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable("id") Long id) {
        attendanceServiceImpl.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
