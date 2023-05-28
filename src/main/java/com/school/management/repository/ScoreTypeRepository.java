package com.school.management.repository;

import com.school.management.model.ScoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreTypeRepository extends JpaRepository<ScoreType, Long> {
	boolean existsByName(String name);
}
