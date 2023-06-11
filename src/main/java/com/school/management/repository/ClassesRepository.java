package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.management.model.Classes;

public interface ClassesRepository extends JpaRepository<Classes, Long> {
    boolean existsByName(String name);

    List<Classes> findByNameContainingIgnoreCase(String name);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.classes.id = :classId")
    boolean hasSchedule(@Param("classId") Long classId);

}
