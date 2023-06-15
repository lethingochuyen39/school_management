package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.school.management.model.Schedule;
import com.school.management.model.ScheduleDetail;
import com.school.management.model.Subject;
import com.school.management.model.Teacher;

public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {

	boolean existsByScheduleAndDayOfWeekAndLessonAndSubjectAndTeacher(Schedule schedule, String dayOfWeek,
			Integer lesson, Subject subject, Teacher teacher);

	List<ScheduleDetail> findBySchedule(Schedule schedule);

	boolean existsByScheduleAndDayOfWeekAndLesson(Schedule schedule, String dayOfWeek,
			Integer lesson);

}
