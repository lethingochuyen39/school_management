package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.model.ReportCard;

public interface ReportCardRepository extends JpaRepository<ReportCard, Long>{
    List<ReportCard> findByStudentId(Long studentId);
}
