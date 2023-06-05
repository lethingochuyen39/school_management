package com.school.management.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.school.management.model.Classes;
import com.school.management.model.Schedule;
import com.school.management.model.Teacher;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.ScheduleRepository;
import com.school.management.repository.SubjectRepository;
import com.school.management.repository.TeacherRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private ClassesRepository classesRepository;
	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public List<Schedule> createSchedules(List<Schedule> schedules, LocalDateTime startTime, LocalDateTime endTime) {
		List<Schedule> scheduleList = new ArrayList<>();
		List<String> errorMessages = new ArrayList<>();

		for (Schedule schedule : schedules) {
			try {
				validateSchedule(schedule); // Kiểm tra ràng buộc
				schedule.setStartTime(startTime); // Đặt giờ bắt đầu cho thời khóa biểu
				schedule.setEndTime(endTime); // Đặt giờ kết thúc cho thời khóa biểu
				Schedule createdSchedule = scheduleRepository.save(schedule);
				scheduleList.add(createdSchedule);

			} catch (IllegalArgumentException e) {
				String errorMessage = "Failed to create schedule: " + e.getMessage();
				errorMessages.add(errorMessage);
			}
		}

		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(String.join("\n", errorMessages));
		}

		return scheduleList;
	}

	@Override
	public Schedule getScheduleById(Long scheduleId) throws NotFoundException {
		return scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new IllegalArgumentException("Schedule not found."));
	}

	@Override
	public List<Schedule> getAllSchedules() {
		return scheduleRepository.findAll();
	}

	@Override
	public Schedule updateSchedule(Long scheduleId, Schedule schedule) {
		validateSchedule(schedule);

		Schedule existingSchedule = getScheduleById(scheduleId);
		existingSchedule.setDayOfWeek(schedule.getDayOfWeek());
		existingSchedule.setLesson(schedule.getLesson());
		existingSchedule.setSubject(schedule.getSubject());
		existingSchedule.setClazz(schedule.getClazz());
		existingSchedule.setTeacher(schedule.getTeacher());
		// existingSchedule.setStartTime(schedule.getStartTime());
		// existingSchedule.setEndTime(schedule.getEndTime());
		return scheduleRepository.save(existingSchedule);
	}

	@Override
	public boolean deleteSchedule(Long scheduleId) {
		Schedule existingSchedule = getScheduleById(scheduleId);
		if (existingSchedule != null) {
			scheduleRepository.delete(existingSchedule);
			return true;
		}
		return false;
	}

	private void validateSchedule(Schedule schedule) {

		List<String> errorMessages = new ArrayList<>();

		// Kiểm tra không được để trống các trường thông tin
		if (schedule.getDayOfWeek() == null) {
			errorMessages.add("Day of week is required");
		}

		if (schedule.getLesson() == null) {
			errorMessages.add("Lesson is required");
		}

		if (schedule.getSubject() == null) {
			errorMessages.add("Subject is required");
		}

		if (schedule.getClazz() == null) {
			errorMessages.add("Class is required");
		}

		if (schedule.getTeacher() == null) {
			errorMessages.add("Teacher is required");
		}

		if (schedule.getStartTime() == null) {
			errorMessages.add("Start time is required");
		}

		if (schedule.getEndTime() == null) {
			errorMessages.add("End time is required");
		}

		// Kiểm tra sự tồn tại của các khóa ngoại
		if (schedule.getSubject() != null && !subjectRepository.existsById(schedule.getSubject().getId())) {
			errorMessages.add("Invalid subject");
		}

		if (schedule.getClazz() != null && !classesRepository.existsById(schedule.getClazz().getId())) {
			errorMessages.add("Invalid class");
		}

		if (schedule.getTeacher() != null && !teacherRepository.existsById(schedule.getTeacher().getId())) {
			errorMessages.add("Invalid teacher");
		}

		// Kiểm tra trùng lịch
		LocalDateTime startTime = schedule.getStartTime();
		LocalDateTime endTime = schedule.getEndTime();
		Classes clazz = schedule.getClazz();
		Teacher teacher = schedule.getTeacher();

		List<Schedule> conflictingSchedules = scheduleRepository.findByClazzAndStartTimeBetween(clazz, startTime,
				endTime);
		if (!conflictingSchedules.isEmpty()) {
			throw new IllegalArgumentException("Trùng lặp lịch học cho lớp học " + clazz.getName());
		}

		conflictingSchedules = scheduleRepository.findByTeacherAndStartTimeBetween(teacher, startTime, endTime);
		if (!conflictingSchedules.isEmpty()) {
			throw new IllegalArgumentException("Trùng lặp lịch dạy cho giáo viên " + teacher.getName());
		}

		List<Schedule> existingSchedules = scheduleRepository.findByDayOfWeekAndLessonAndClazz(schedule.getDayOfWeek(),
				schedule.getLesson(), schedule.getClazz());

		if (!existingSchedules.isEmpty()) {
			errorMessages.add("Schedule already exists for the given day, lesson, and class");
		}

		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(String.join("\n", errorMessages));
		}
	}

	@Override
	public List<Schedule> getSchedulesByClassAndTime(Long classId, LocalDateTime startTime, LocalDateTime endTime) {
		Classes clazz = classesRepository.findById(classId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lớp học với ID: " + classId));

		return scheduleRepository.findByClazzAndStartTimeBetween(clazz, startTime, endTime);
	}

}