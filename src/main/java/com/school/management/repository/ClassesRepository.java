package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Classes;

public interface ClassesRepository extends JpaRepository<Classes, Long> {
    boolean existsByName(String name);

    List<Classes> findByNameContainingIgnoreCase(String name);
}
