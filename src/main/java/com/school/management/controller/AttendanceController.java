package com.school.management.controller;

import org.springframework.http.HttpStatus;
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

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/create")
    public ResponseEntity<Attendance> createAttendance(@RequestBody AttendanceDto attendanceDto) {
        Attendance createdAttendance = attendanceService.createAttendance(attendanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendance);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id,
            @RequestBody AttendanceDto attendanceDto) {
        Attendance updatedAttendance = attendanceService.updateAttendance(id, attendanceDto);
        return ResponseEntity.ok(updatedAttendance);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }
}
