package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.MetricDto;
import com.school.management.model.AcademicYear;
import com.school.management.model.Metric;
import com.school.management.repository.AcademicYearRespository;
import com.school.management.repository.MetricRepository;

@Service
public class MetricServiceImpl implements MetricService{

    @Autowired
    private MetricRepository metricRepository;

	@Autowired
    private AcademicYearRespository academicYearRespository;

    @Override
    public Metric createMetric(MetricDto metricDto) {
		Long academicYearId = metricDto.getAcademicYearId();

        AcademicYear academicYear = academicYearRespository.findById(academicYearId)
            .orElseThrow(() -> new IllegalArgumentException("Academic Year not found with id: " + academicYearId));

		Metric metric = new Metric();
		metric.setName(metricDto.getName());
		metric.setDescription(metricDto.getDescription());
		metric.setValue(metricDto.getValue());
		metric.setAcademicYear(academicYear);

		return metricRepository.save(metric);
    }

    @Override
    public Metric getMetricById(Long id) {
        return metricRepository.findById(id)
				.orElseThrow(() -> new MetricNotFoundException("Metric not found with id: " + id));

    }

    @Override
    public Metric updateMetric(Long id, MetricDto metricDto) {
		Metric existingMetric = metricRepository.findById(id)
				.orElseThrow(() -> new MetricNotFoundException("Metric not found with id: " + id));
		existingMetric.setName(metricDto.getName());
		existingMetric.setDescription(metricDto.getDescription());
		existingMetric.setValue(metricDto.getValue());
		
		return metricRepository.save(existingMetric);
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
