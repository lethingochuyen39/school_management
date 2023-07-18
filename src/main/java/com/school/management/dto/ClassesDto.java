package com.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ClassesDto {
    private String name;
    private String description;
    private String grade;
    private Long teacherId;
    private Long academicYearId;
    private Integer limitStudent;
    private Boolean isActive;
}
