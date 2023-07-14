package com.school.management.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ReportCards")
public class ReportCard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@Column(name = "violate", columnDefinition = "NVARCHAR(255)")
	private String violate;

	@Column(name = "description", columnDefinition = "NVARCHAR(255)")
	private String description;

	@Column(name = "date")
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "academic_year_id", nullable = false)
	private AcademicYear academicYear;

}
