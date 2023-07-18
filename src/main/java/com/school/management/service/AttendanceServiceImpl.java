package com.school.management.service;

import org.springframework.stereotype.Service;

import com.school.management.dto.AttendanceDto;
import com.school.management.model.Attendance;
import com.school.management.repository.AttendanceRepository;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.SubjectRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

        private final AttendanceRepository attendanceRepository;
        private final SubjectRepository subjectRepository;
        private final StudentRepository studentRepository;
        private final ClassesRepository classesRepository;

        public AttendanceServiceImpl(AttendanceRepository attendanceRepository, SubjectRepository subjectRepository,
                        StudentRepository studentRepository, ClassesRepository classesRepository) {
                this.attendanceRepository = attendanceRepository;
                this.subjectRepository = subjectRepository;
                this.studentRepository = studentRepository;
                this.classesRepository = classesRepository;
        }

        @Override
        public Attendance createAttendance(AttendanceDto attendanceDto) {
                Attendance attendance = new Attendance();
                attendance.setDate(attendanceDto.getDate());
                attendance.setIsPresent(attendanceDto.getIsPresent());
                attendance.setNote(attendanceDto.getNote());

                // Lấy subject từ subjectRepository
                subjectRepository.findById(attendanceDto.getSubjectId()).ifPresent(attendance::setSubject);

                // Lấy student từ studentRepository
                studentRepository.findById(attendanceDto.getStudentId()).ifPresent(attendance::setStudent);

                // Lấy classes từ classesRepository
                classesRepository.findById(attendanceDto.getClassId()).ifPresent(attendance::setClasses);

                return attendanceRepository.save(attendance);
        }

        @Override
        public Attendance updateAttendance(Long id, AttendanceDto attendanceDto) {
                Attendance existingAttendance = attendanceRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Attendance not found with id: " + id));

                existingAttendance.setDate(attendanceDto.getDate());
                existingAttendance.setIsPresent(attendanceDto.getIsPresent());
                existingAttendance.setNote(attendanceDto.getNote());

                // Lấy subject từ subjectRepository
                subjectRepository.findById(attendanceDto.getSubjectId()).ifPresent(existingAttendance::setSubject);

                // Lấy student từ studentRepository
                studentRepository.findById(attendanceDto.getStudentId()).ifPresent(existingAttendance::setStudent);

                // Lấy classes từ classesRepository
                classesRepository.findById(attendanceDto.getClassId()).ifPresent(existingAttendance::setClasses);

                return attendanceRepository.save(existingAttendance);
        }

        @Override
        public boolean deleteAttendance(Long id) {
                Attendance attendance = attendanceRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Attendance not found with id: " + id));

                attendanceRepository.delete(attendance);
                return true;
        }

        @Override
        public Attendance getAttendanceById(Long id) {
                return attendanceRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Attendance not found with id: " + id));
        }
}
