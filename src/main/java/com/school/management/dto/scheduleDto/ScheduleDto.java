package com.school.management.dto.scheduleDto;

import com.school.management.model.ScheduleStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ScheduleDto {

	private Long dayOfWeekId;
	private Long lessonId;
	private Long subjectId;
	private Long classId;
	private Long teacherId;

	private ScheduleStatus status;

}
