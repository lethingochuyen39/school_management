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
public class EvaluationRecordDto {
    private Long studentId;
    private String disciplineReason;
    private String achievement;
    private LocalDate date;
}
