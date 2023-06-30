package com.school.management.dto.scheduleDto;

import java.time.LocalDate;
import java.util.List;

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
	// private Integer semester;
	private Long classesId;
	private List<ScheduleDetailDto> scheduleDetails;

}
