package com.school.management.model;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "status", nullable = false)
	private String status;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Set<Role> roles = new HashSet<>();

	public User(String username, String email, String status, String password) {
		this.username = username;
		this.email = email;
		this.status = status;
		this.password = password;
	}

}
