package com.school.management.dto;

import java.time.LocalDate;

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
public class TeacherDto {
	private String name;
	private String gender;
	private LocalDate dob;
	private String email;
	private String address;
	private String phone;
	private String status;

	// private Long userId;
}
