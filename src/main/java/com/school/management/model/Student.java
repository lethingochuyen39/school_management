package com.school.management.model;

import java.time.LocalDate;
import java.util.List;

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
@Table(name = "Students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "gender")
	private String gender;

	@Column(name = "dob")
	private LocalDate dob;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;

	@Column(name = "status")
	private String status;

	@Column(name = "image")
	private String image;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JsonIgnore
	// @JoinColumn(name = "class_id", nullable = false)
	// private Classes className;
	@ManyToMany
	@JoinTable(
  		name = "student_class", 
  		joinColumns = @JoinColumn(name = "student_id"), 
  		inverseJoinColumns = @JoinColumn(name = "class_id"))
	private List<Classes> className;

	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "user_id", unique = true, nullable = true)
	private User user;

}
