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
public class ReportCardDto {
    private Long studentId;
    private String violate;
    private String description;
    private LocalDate date;
    private Long academicYearId;

}
