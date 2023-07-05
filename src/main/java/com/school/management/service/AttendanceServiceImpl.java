package com.school.management.service;

import com.school.management.model.Attendance;
import com.school.management.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    @Override
    public Attendance updateAttendance(Long id, Attendance attendance) {
        Attendance existingAttendance = attendanceRepository.findById(id).orElse(null);
        if (existingAttendance != null) {
            attendance.setId(existingAttendance.getId());
            return attendanceRepository.save(attendance);
        }
        return null;
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}
