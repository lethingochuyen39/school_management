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
public class StudentDTO {
    private String name;

	private String gender;

	private LocalDate dob;

	private String email;

	private String address;

	private String phone;

	private String status;

	private String image;
    
    private String className;
}
