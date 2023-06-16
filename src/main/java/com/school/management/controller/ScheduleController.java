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

import com.school.management.dto.ScheduleDto;
import com.school.management.model.Schedule;
import com.school.management.service.ScheduleServiceImpl;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

	@Autowired
	private ScheduleServiceImpl scheduleServiceImpl;

	@PostMapping("/add")
	public ResponseEntity<?> createSchedule(@RequestBody ScheduleDto scheduleDto) {
		try {
			ScheduleDto createSchedule = scheduleServiceImpl.creaSchedule(scheduleDto);
			return ResponseEntity.ok().body(createSchedule);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
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
		}
	}

	@GetMapping
	public ResponseEntity<List<Schedule>> getAllScheduls() {
		List<Schedule> schedules = scheduleServiceImpl.getAllSchedules();
		return ResponseEntity.ok(schedules);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateSchedule(@PathVariable("id") Long id, @RequestBody ScheduleDto scheduleDto) {
		try {
			ScheduleDto updatedSchedule = scheduleServiceImpl.updateSchedule(id, scheduleDto);
			return ResponseEntity.ok(updatedSchedule);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/search")
	public ResponseEntity<List<?>> searchSchedulesByClassName(@RequestParam("className") String className) {
		List<ScheduleDto> schedules = scheduleServiceImpl.getSchedulesByClassName(className);
		return ResponseEntity.ok(schedules);
	}

}
