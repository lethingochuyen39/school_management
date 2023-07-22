package com.school.management.service;

import java.util.List;

import com.school.management.dto.MetricDto;
import com.school.management.model.Metric;

public interface MetricService {
    
	Metric createMetric(MetricDto metricDto);

	Metric getMetricById(Long id);

	Metric updateMetric(Long id, MetricDto metricDto);

	boolean deleteMetric(Long id);

	List<Metric> getAllMetric();

	List<Metric> getMetricByName(String name);

}