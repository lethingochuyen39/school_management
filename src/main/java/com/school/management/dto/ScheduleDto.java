package com.school.management.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ScheduleDto {
	private Long id;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer semester;
	private Long classesId;
	private Long AcademicYearId;

}
