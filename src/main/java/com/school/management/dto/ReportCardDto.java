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
public class ReportCardDto {
    private Long studentId;
    private BigDecimal averageScore;
    private Long academicYearId;

}
