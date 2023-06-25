package com.school.management.dto;

import com.school.management.model.AcademicYear;
import com.school.management.model.Teacher;

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
    private Long id;
    private String name;

    private String description;

    private Teacher teacher;

    private AcademicYear academicYear;

    private String grade;
}
