package com.school.management.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "Teachers")
public class Teacher implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String name;

	@Column(name = "gender", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String gender;

	@Column(name = "dob")
	private LocalDate dob;

	@Column(name = "email", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String email;

	@Column(name = "address", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String address;

	@Column(name = "phone", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String phone;

	@Column(name = "status", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String status;

	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "user_id", unique = true, nullable = true)
	private User user;

	// @Column(name = "image")
	// private String image;

	// @Column(name = "file_name", nullable = false, columnDefinition =
	// "NVARCHAR(255)")
	// private String fileName;

	// @Column(name = "file_path", columnDefinition = "NVARCHAR(MAX)")
	// private String filePath;

	// @JsonIgnore
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "uploaded_by")
	// private User uploadedBy;
}
