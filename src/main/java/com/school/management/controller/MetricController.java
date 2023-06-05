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
import org.springframework.web.bind.annotation.RestController;

import com.school.management.model.Metric;
import com.school.management.service.MetricServiceImpl;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

	@Autowired
	private MetricServiceImpl metricServiceImpl;

	@PostMapping
	public ResponseEntity<?> createMetric(@RequestBody Metric metric) {
		try {
			Metric createdMetric = metricServiceImpl.createMetric(metric);
			return ResponseEntity.ok().body(createdMetric);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); // Thông báo lỗi nếu không thể tạo Metric
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getMetricById(@PathVariable Long id) {
		try {
			Metric metric = metricServiceImpl.getMetricById(id);
			return ResponseEntity.ok(metric);
		} catch (MetricServiceImpl.MetricNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateMetric(@PathVariable Long id,
			@RequestBody Metric metric) {
		try {
			Metric updatedMetric = metricServiceImpl.updateMetric(id, metric);
			return ResponseEntity.ok(updatedMetric);
		} catch (MetricServiceImpl.MetricNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); // Thông báo lỗi nếu không thể cập nhật //
																		// Metric
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMetric(@PathVariable Long id) {
		try {
			metricServiceImpl.deleteMetric(id);
			return ResponseEntity.ok().build();
		} catch (MetricServiceImpl.MetricNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Metric>> getAllMetrics() {
		List<Metric> metric = metricServiceImpl.getAllMetric();
		return ResponseEntity.ok(metric);
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<Metric>> searchMetricByName(@PathVariable(value = "name") String name) {
		List<Metric> metric = metricServiceImpl.getMetricByName(name);
		return ResponseEntity.ok(metric);
	}
}
