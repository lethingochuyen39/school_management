package com.school.management.dto.scheduleDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ScheduleDetailDto {
	private Integer lesson;
	private String dayOfWeek;
	private SubjectDto subject;
	private TeacherDto teacher;
	private Long scheduleId;

}
