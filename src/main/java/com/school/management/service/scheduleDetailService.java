package com.school.management.service;

import java.util.List;

import com.school.management.dto.scheduleDto.ScheduleDetailDto;
import com.school.management.model.ScheduleDetail;

public interface scheduleDetailService {
	void saveScheduleDetails(Long scheduleId, List<ScheduleDetailDto> scheduleDetailDtos);

	void updateScheduleDetail(Long scheduleDetailId, ScheduleDetailDto scheduleDetailDto);

	void deleteScheduleDetail(Long scheduleDetailId);

	ScheduleDetail getScheduleDetailById(Long scheduleDetailId);
}
