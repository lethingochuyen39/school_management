package com.school.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.dto.scheduleDto.ScheduleDetailDto;
import com.school.management.model.ScheduleDetail;
import com.school.management.service.scheduleDetailServiceImpl;

@RestController
@RequestMapping("/api/schedule-details")
public class ScheduleDetailController {

	@Autowired
	private scheduleDetailServiceImpl scheduleDetailServiceImpl;

	@PostMapping("/schedule/{scheduleId}/add")
	public ResponseEntity<String> addScheduleDetails(@PathVariable("scheduleId") Long scheduleId,
			@RequestBody List<ScheduleDetailDto> scheduleDetails) {
		try {

			scheduleDetailServiceImpl.saveScheduleDetails(scheduleId, scheduleDetails);
			return ResponseEntity.ok("Thêm danh sách chi tiết thời khóa biểu thành công.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getScheduleById(@PathVariable Long id) {
		try {
			ScheduleDetail scheduledDetail = scheduleDetailServiceImpl.getScheduleDetailById(id);
			return ResponseEntity.ok(scheduledDetail);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
		try {
			scheduleDetailServiceImpl.deleteScheduleDetail(id);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateSchedule(@PathVariable("id") Long id,
			@RequestBody ScheduleDetailDto scheduleDetailDto) {
		try {
			scheduleDetailServiceImpl.updateScheduleDetail(id, scheduleDetailDto);
			return ResponseEntity.ok("Cập nhật chi tiết thời khóa biểu thành công.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
