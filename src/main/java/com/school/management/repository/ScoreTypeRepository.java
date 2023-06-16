package com.school.management.repository;

import com.school.management.model.ScoreType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreTypeRepository extends JpaRepository<ScoreType, Long> {
	boolean existsByName(String name);
}
