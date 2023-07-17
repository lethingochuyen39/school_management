package com.school.management.service;

import java.util.List;

import com.school.management.dto.scheduleDto.ScheduleDto;
import com.school.management.model.Schedule;
import com.school.management.model.ScheduleStatus;

public interface ScheduleService {
	Schedule addSchedule(ScheduleDto scheduleDTO);

	List<Schedule> getAllSchedules();

	List<Schedule> getSchedulesByClass(Long classId);

	Schedule updateScheduleStatus(Long scheduleId, ScheduleStatus status);
	// ScheduleDto creaSchedule(ScheduleDto scheduleDto);

	Schedule getScheduleById(long id);

	void deleteSchedule(long id);

	Schedule updateSchedule(Long scheduleId, ScheduleDto scheduleDto);

	List<Schedule> getSchedulesByClassName(String className);

	List<Schedule> searchSchedule(String className);

	List<Schedule> getTeacherSchedule(Long teacherId);
}
