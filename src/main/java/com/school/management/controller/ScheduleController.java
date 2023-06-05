package com.school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.school.management.model.Schedule;
import com.school.management.service.ScheduleServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

	@Autowired
	private ScheduleServiceImpl scheduleServiceImpl;

	@PostMapping
	public ResponseEntity<?> createSchedules(@RequestBody List<Schedule> schedules,
			@RequestParam LocalDateTime startTime,
			@RequestParam LocalDateTime endTime) {
		try {
			List<Schedule> createdSchedules = scheduleServiceImpl.createSchedules(schedules, startTime, endTime);
			return ResponseEntity.ok(createdSchedules);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
		}
	}

	@GetMapping("/{scheduleId}")
	public ResponseEntity<?> getScheduleById(@PathVariable Long scheduleId) {
		try {
			Schedule schedule = scheduleServiceImpl.getScheduleById(scheduleId);
			return ResponseEntity.ok(schedule);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Schedule>> getAllSchedules() {
		List<Schedule> schedules = scheduleServiceImpl.getAllSchedules();
		return ResponseEntity.ok(schedules);
	}

	@PutMapping("/{scheduleId}")
	public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId, @RequestBody Schedule schedule) {
		try {
			Schedule updatedSchedule = scheduleServiceImpl.updateSchedule(scheduleId, schedule);
			return ResponseEntity.ok(updatedSchedule);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
		}
	}

	@DeleteMapping("/{scheduleId}")
	public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) {
		if (scheduleServiceImpl.deleteSchedule(scheduleId)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.noContent().build();

		}
	}

	@GetMapping("/class/{classId}")
	public ResponseEntity<?> getSchedulesByClassAndTime(@PathVariable Long classId,
			@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
		try {
			List<Schedule> schedules = scheduleServiceImpl.getSchedulesByClassAndTime(classId, startTime, endTime);
			return ResponseEntity.ok(schedules);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
