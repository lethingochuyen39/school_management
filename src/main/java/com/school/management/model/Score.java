package com.school.management.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "Scores")
public class Score {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	// @JsonIgnore
	@JoinColumn(name = "subject_id", nullable = false)
	private Subject subject;

	@ManyToOne(fetch = FetchType.LAZY)
	// @JsonIgnore
	@JoinColumn(name = "score_type_id", nullable = false)
	private ScoreType scoreType;

	@Column(name = "score")
	private BigDecimal score;

}
