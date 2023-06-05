package com.school.management.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Classes;
import com.school.management.model.Schedule;
import com.school.management.model.Teacher;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findByClazz(Classes clazz);

	List<Schedule> findByClazzAndStartTimeBetween(Classes clazz, LocalDateTime startTime, LocalDateTime endTime);

	List<Schedule> findByTeacherAndStartTimeBetween(Teacher teacher, LocalDateTime startTime, LocalDateTime endTime);

	List<Schedule> findByDayOfWeekAndLessonAndClazz(String dayOfWeek, Integer lesson, Classes clazz);
}
