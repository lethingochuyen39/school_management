package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.dto.scheduleDto.ScheduleDto;
import com.school.management.model.Schedule;
import com.school.management.model.ScheduleStatus;
import com.school.management.service.ScheduleServiceImpl;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

	@Autowired
	private ScheduleServiceImpl scheduleServiceImpl;

	@GetMapping
	public ResponseEntity<List<?>> getAllScheduls(@RequestParam(required = false) String className) {
		List<Schedule> schedules = scheduleServiceImpl.searchSchedule(className);
		return ResponseEntity.ok(schedules);
	}

	@PostMapping
	public ResponseEntity<?> addSchedule(@RequestBody ScheduleDto scheduleDto) {
		try {
			Schedule addedSchedule = scheduleServiceImpl.addSchedule(scheduleDto);
			return ResponseEntity.ok(addedSchedule);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("/class/{classId}")
	public List<Schedule> getSchedulesByClass(@PathVariable Long classId) {
		return scheduleServiceImpl.getSchedulesByClass(classId);
	}

	@PutMapping("/{scheduleId}/status")
	public Schedule updateScheduleStatus(@PathVariable Long scheduleId, @RequestParam ScheduleStatus status) {
		return scheduleServiceImpl.updateScheduleStatus(scheduleId, status);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getScheduleById(@PathVariable Long id) {
		try {
			Schedule schedule = scheduleServiceImpl.getScheduleById(id);
			return ResponseEntity.ok(schedule);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
		try {
			scheduleServiceImpl.deleteSchedule(id);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{scheduleId}")
	public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId,
			@RequestBody ScheduleDto scheduleDto) {
		try {
			Schedule schedule = scheduleServiceImpl.updateSchedule(scheduleId, scheduleDto);
			return ResponseEntity.ok(schedule);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}