package com.school.management.service;

import com.school.management.dto.AttendanceDto;
import com.school.management.model.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance createAttendance(AttendanceDto attendanceDto);

    Attendance updateAttendance(Long id, AttendanceDto attendanceDto);

    Attendance getAttendanceById(Long id);

    List<Attendance> getAllAttendances();

    void deleteAttendance(Long id);
}
