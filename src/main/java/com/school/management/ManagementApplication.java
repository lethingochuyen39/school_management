package com.school.management;

import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.school.management.model.DayOfWeek;
import com.school.management.model.Lesson;
import com.school.management.model.Role;
import com.school.management.model.UserRole;
import com.school.management.repository.DayOfWeekRepository;
import com.school.management.repository.LessonRepository;
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
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository,
			DayOfWeekRepository dayOfWeekRepository, LessonRepository lessonRepository) {
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

			// huyen
			// if (dayOfWeekRepository.count() == 0) {
			// addDayOfWeek(dayOfWeekRepository, "Thứ 2");
			// addDayOfWeek(dayOfWeekRepository, "Thứ 3");
			// addDayOfWeek(dayOfWeekRepository, "Thứ 4");
			// addDayOfWeek(dayOfWeekRepository, "Thứ 5");
			// addDayOfWeek(dayOfWeekRepository, "Thứ 6");

			// }

			// if (lessonRepository.count() == 0) {
			// addLesson(lessonRepository, "Tiết 1", "07:00:00", "07:45:00");
			// addLesson(lessonRepository, "Tiết 2", "07:50:00", "08:35:00");
			// addLesson(lessonRepository, "Tiết 3", "09:00:00", "09:45:00");
			// addLesson(lessonRepository, "Tiết 4", "09:50:00", "10:35:00");
			// addLesson(lessonRepository, "Tiết 5", "10:40:00", "11:25:00");
			// addLesson(lessonRepository, "Tiết 6", "13:00:00", "13:45:00");
			// addLesson(lessonRepository, "Tiết 7", "13:50:00", "14:35:00");
			// addLesson(lessonRepository, "Tiết 8", "15:00:00", "15:45:00");
			// addLesson(lessonRepository, "Tiết 9", "15:50:00", "16:35:00");
			// addLesson(lessonRepository, "Tiết 10", "16:40:00", "17:25:00");
			// }
		};
	}

	// huyen
	// private void addDayOfWeek(DayOfWeekRepository dayOfWeekRepository, String
	// name) {
	// DayOfWeek dayOfWeek = dayOfWeekRepository.findByName(name);
	// if (dayOfWeek == null) {
	// dayOfWeek = new DayOfWeek();
	// dayOfWeek.setName(name);
	// dayOfWeekRepository.save(dayOfWeek);
	// }
	// }

	// private void addLesson(LessonRepository lessonRepository, String name, String
	// startTime, String endTime) {
	// Lesson lesson = lessonRepository.findByName(name);
	// if (lesson == null) {
	// lesson = new Lesson();
	// lesson.setName(name);
	// lesson.setStartTime(LocalTime.parse(startTime));
	// lesson.setEndTime(LocalTime.parse(endTime));
	// lessonRepository.save(lesson);
	// }
	// }

}
