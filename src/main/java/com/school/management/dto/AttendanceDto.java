package com.school.management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceDto {
    private Long subjectId;
    private Long studentId;
    private Long classId;
    private LocalDate date;
    private Boolean isPresent;
    private String note;
}
