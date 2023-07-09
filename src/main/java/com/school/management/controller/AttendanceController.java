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

import com.school.management.dto.AttendanceDto;
import com.school.management.model.Attendance;
import com.school.management.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/create")
    public ResponseEntity<Attendance> createAttendance(@RequestBody AttendanceDto attendanceDto) {
        Attendance createdAttendance = attendanceService.createAttendance(attendanceDto);
        return ResponseEntity.ok(createdAttendance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable("id") Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        if (attendance != null) {
            return ResponseEntity.ok(attendance);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable("id") Long id,
            @RequestBody AttendanceDto attendanceDto) {
        Attendance updatedAttendance = attendanceService.updateAttendance(id, attendanceDto);
        if (updatedAttendance != null) {
            return ResponseEntity.ok(updatedAttendance);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable("id") Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
