package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Metric;

public interface MetricRepository extends JpaRepository<Metric, Long> {

    boolean existsByName(String name);

	List<Metric> findByNameContainingIgnoreCase(String name);
}
