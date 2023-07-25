package com.school.management.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "Classes")
public class Classes implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String name;

	@Column(name = "description", nullable = true, columnDefinition = "NVARCHAR(255)")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id", nullable = false)
	private Teacher teacher;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "academic_year_id", nullable = false)
	private AcademicYear academicYear;

	@Column(name = "grade", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String grade;

	@ManyToMany(mappedBy = "className", cascade = CascadeType.ALL)
	@JsonBackReference
	List<Student> students;

	@Column(name = "limitStudent", nullable = false)
	private Integer limitStudent;

	// @Column(name = "isActive")
	// private Boolean isActive;

}
