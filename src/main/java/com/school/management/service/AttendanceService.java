package com.school.management.service;

import com.school.management.dto.AttendanceDto;
import com.school.management.model.Attendance;

public interface AttendanceService {
    Attendance createAttendance(AttendanceDto attendanceDto);

    Attendance updateAttendance(Long id, AttendanceDto attendanceDto);

    boolean deleteAttendance(Long id);

    Attendance getAttendanceById(Long id);
}
