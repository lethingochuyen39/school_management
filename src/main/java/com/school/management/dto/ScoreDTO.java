package com.school.management.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ScoreDTO {
	private Long studentId;
	private Long subjectId;
	private Long scoreTypeId;
	private Long classId;
	private Integer semester;
	private BigDecimal score;

}
