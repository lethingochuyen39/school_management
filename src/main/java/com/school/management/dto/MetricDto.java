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
public class MetricDto {
    private String name;
    private String description;
    private BigDecimal value;
    private Long academicYearId;

}
