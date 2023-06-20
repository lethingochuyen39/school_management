package com.school.management.service;

import java.util.List;

import com.school.management.dto.scheduleDto.ScheduleDto;
import com.school.management.model.Schedule;

public interface ScheduleService {

	ScheduleDto creaSchedule(ScheduleDto scheduleDto);

	Schedule getScheduleById(long id);

	List<Schedule> getAllSchedules();

	void deleteSchedule(long id);

	ScheduleDto updateSchedule(Long scheduleId, ScheduleDto scheduleDto);

	List<Schedule> getSchedulesByClassName(String className);
}
