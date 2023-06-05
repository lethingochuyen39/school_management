package com.school.management.service;

import java.time.LocalDateTime;
import java.util.List;

import com.school.management.model.Schedule;

public interface ScheduleService {
	List<Schedule> createSchedules(List<Schedule> schedules, LocalDateTime startTime, LocalDateTime endTime);

	Schedule getScheduleById(Long scheduleId);

	List<Schedule> getAllSchedules();

	Schedule updateSchedule(Long scheduleId, Schedule schedule);

	boolean deleteSchedule(Long scheduleId);

	List<Schedule> getSchedulesByClassAndTime(Long classId, LocalDateTime startTime, LocalDateTime endTime);
}
