package com.school.management.service;

import java.util.List;

import com.school.management.model.ReportCard;

public interface ReportCardService {
    ReportCard createReportCard(ReportCard reportCard);

	ReportCard getReportCardById(Long id);

    List<ReportCard> getReportCardByStudentId(Long studentId);

	ReportCard updateReportCard(Long id, ReportCard reportCard);

	void deleteReportCard(Long id);

	List<ReportCard> getReportCard();  
}
