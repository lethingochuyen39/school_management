package com.school.management.model;

import java.io.Serializable;
import jakarta.persistence.*;
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
	@JoinColumn(name = "day_of_week_id", nullable = false)
	private DayOfWeek dayOfWeek;

	@ManyToOne
	@JoinColumn(name = "lesson_id", nullable = false)
	private Lesson lesson;
	@ManyToOne
	@JoinColumn(name = "subject_id", nullable = false)
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "class_id", nullable = false)
	private Classes classes;

	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private Teacher teacher;

	@Enumerated(EnumType.STRING)
	private ScheduleStatus status;

}