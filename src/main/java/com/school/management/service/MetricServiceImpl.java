package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.Metric;
import com.school.management.repository.MetricRepository;

@Service
public class MetricServiceImpl implements MetricService{

    @Autowired
    private MetricRepository metricRepository;

    @Override
    public Metric createMetric(Metric metric) {
        if (metric.getName() == null || metric.getDescription() == null
				|| metric.getValue() == null || metric.getYear() == null) {
			throw new IllegalArgumentException("Name, description, value, and year are required.");
		}

		if (metricRepository.existsByName(metric.getName())) {
			throw new IllegalArgumentException("Metric with the same name already exists.");
		}

		return metricRepository.save(metric);
    }

    @Override
    public Metric getMetricById(Long id) {
        return metricRepository.findById(id)
				.orElseThrow(() -> new MetricNotFoundException("Metric not found with id: " + id));

    }

    @Override
    public Metric updateMetric(Long id, Metric metric) {

		if (!metricRepository.existsById(id)) {
			throw new MetricNotFoundException("Metric not found with id: " + id);
		}

		if (metric.getName() == null || metric.getDescription() == null
				|| metric.getValue() == null || metric.getYear() == null) {
			throw new IllegalArgumentException("Name, description, value, and year are required.");
		}

		Metric existingMetric = metricRepository.findById(id)
				.orElseThrow(() -> new MetricNotFoundException("Metric not found with id: " + id));

		if (existingMetric != null && !existingMetric.getName().equals(metric.getName())) {
			if (metricRepository.existsByName(metric.getName())) {
				throw new IllegalArgumentException("Academic year with the same name already exists.");
			}
		}
		metric.setId(id);
		return metricRepository.save(metric);
    }

    @Override
    public boolean deleteMetric(Long id) {
        if (!metricRepository.existsById(id)) {
			throw new MetricNotFoundException("Metric not found with id: " + id);
		}
		metricRepository.deleteById(id);
		return true;
    }

    @Override
    public List<Metric> getAllMetric() {
        return metricRepository.findAll();
    }

    @Override
    public List<Metric> getMetricByName(String name) {
        return metricRepository.findByNameContainingIgnoreCase(name);
    }

    public class MetricNotFoundException extends RuntimeException {
		public MetricNotFoundException(String message) {
			super(message);
		}
	}
    
}
