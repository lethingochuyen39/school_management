package com.school.management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.school.management.model.Role;
import com.school.management.model.UserRole;
import com.school.management.repository.RoleRepository;
import com.school.management.repository.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SpringBootApplication
@EnableJpaRepositories
@OpenAPIDefinition(info = @Info(title = "springdoc-openapi", version = "1.0.0"), security = {
		@SecurityRequirement(name = "bearer-key") })
public class ManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {
		return args -> {

			Role adminRole = roleRepository.findByRole(UserRole.ADMIN);
			if (adminRole == null) {
				adminRole = new Role();
				adminRole.setRole(UserRole.ADMIN);
				roleRepository.save(adminRole);
			}

			Role studentRole = roleRepository.findByRole(UserRole.STUDENT);
			if (studentRole == null) {
				studentRole = new Role();
				studentRole.setRole(UserRole.STUDENT);
				roleRepository.save(studentRole);
			}

			Role parentRole = roleRepository.findByRole(UserRole.PARENTS);
			if (parentRole == null) {
				parentRole = new Role();
				parentRole.setRole(UserRole.PARENTS);
				roleRepository.save(parentRole);
			}

			Role teacherRole = roleRepository.findByRole(UserRole.TEACHER);
			if (teacherRole == null) {
				teacherRole = new Role();
				teacherRole.setRole(UserRole.TEACHER);
				roleRepository.save(teacherRole);
			}
		};
	}

}
