package com.school.management.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	@Column(name = "start_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	@Column(name = "semester", nullable = false)
	private Integer semester;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", nullable = false)
	@JsonIgnore
	private Classes classes;

	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonProperty("scheduleDetails")
	private List<ScheduleDetail> scheduleDetails = new ArrayList<>();
}