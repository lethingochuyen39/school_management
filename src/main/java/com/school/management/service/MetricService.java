package com.school.management.service;

import java.util.List;

import com.school.management.model.Metric;

public interface MetricService {
    
	Metric createMetric(Metric metric);

	Metric getMetricById(Long id);

	Metric updateMetric(Long id, Metric metric);

	boolean deleteMetric(Long id);

	List<Metric> getAllMetric();

	List<Metric> getMetricByName(String name);

}