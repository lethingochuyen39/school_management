package com.school.management.dto;

import java.util.HashSet;
import java.util.Set;

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
public class SubjectDto {
    private String name;
    private Set<Teacher> teachers = new HashSet<>();
}
