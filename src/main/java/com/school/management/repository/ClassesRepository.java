package com.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.Classes;

public interface ClassesRepository extends JpaRepository<Classes, Long> {

}
