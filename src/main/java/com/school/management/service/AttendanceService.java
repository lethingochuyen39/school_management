package com.school.management.service;

import com.school.management.model.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance createAttendance(Attendance attendance);

    Attendance getAttendanceById(Long id);

    Attendance updateAttendance(Long id, Attendance attendance);

    List<Attendance> getAllAttendances();

    void deleteAttendance(Long id);
}
