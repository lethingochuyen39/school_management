package com.school.management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Schedule;
import com.school.management.model.ScheduleStatus;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findByClassesNameContainingIgnoreCase(String className);

	boolean existsByDayOfWeekIdAndLessonIdAndSubjectIdAndTeacherId(
			Long dayOfWeekId, Long lessonId, Long subjectId, Long teacherId);

	boolean existsByDayOfWeekIdAndLessonIdAndSubjectIdAndTeacherIdAndClassesIdAndStatus(
			long dayOfWeekId, long lessonId, long subjectId, long teacherId, long classId, ScheduleStatus status);

	boolean existsByTeacherIdAndDayOfWeekIdAndLessonIdAndStatus(
			Long teacherId, Long dayOfWeekId, Long lessonId, ScheduleStatus status);

	boolean existsByClassesIdAndDayOfWeekIdAndLessonId(Long classId, Long dayOfWeekId, Long lessonId);

	boolean existsByDayOfWeekIdAndLessonIdAndSubjectIdAndTeacherIdAndClassesId(
			long dayOfWeekId, long lessonId, long subjectId, long teacherId, long classId);

	List<Schedule> findByClassesId(Long classId);

	List<Schedule> findByTeacherId(Long teacherId);
}
