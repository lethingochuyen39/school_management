package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.AttendanceDto;
import com.school.management.model.Attendance;
import com.school.management.model.Classes;
import com.school.management.model.Student;
import com.school.management.repository.AttendanceRepository;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.StudentRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassesRepository classesRepository;

    @Override
    public Attendance createAttendance(AttendanceDto attendanceDto) {
        Long studentId = attendanceDto.getStudentId();
        Long classId = attendanceDto.getClassId();

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));
        Classes classes = classesRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with id: " + classId));

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setClasses(classes);
        attendance.setDate(attendance.getDate());
        attendance.setStatus(attendanceDto.getStatus());

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    @Override
    public Attendance updateAttendance(Long id, AttendanceDto attendanceDto) {
		Attendance existingAttendance = attendanceRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Attendance not found with id: " + id));

		Long studentId = attendanceDto.getStudentId();
		Long classId = attendanceDto.getClassId();

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));
		Classes classes = classesRepository.findById(classId)
				.orElseThrow(() -> new IllegalArgumentException("Class not found with id: " + classId));
		

		existingAttendance.setStudent(student);
		existingAttendance.setClasses(classes);
		existingAttendance.setDate(attendanceDto.getDate());
        existingAttendance.setStatus(attendanceDto.getStatus());

		return attendanceRepository.save(existingAttendance);
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
