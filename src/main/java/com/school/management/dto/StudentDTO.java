package com.school.management.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
	private Long id;
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
