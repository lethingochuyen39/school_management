package com.school.management.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Schedule")
public class Schedule implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	// @NotNull
	@JoinColumn(name = "day_of_week_id")
	private DayOfWeek dayOfWeek;

	@ManyToOne
	// @NotNull
	@JoinColumn(name = "lesson_id")
	private Lesson lesson;

	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;

	@ManyToOne
	// @NotNull
	@JoinColumn(name = "class_id")
	private Classes classes;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	// @NotBlank
	@Enumerated(EnumType.STRING)
	private ScheduleStatus status;

}