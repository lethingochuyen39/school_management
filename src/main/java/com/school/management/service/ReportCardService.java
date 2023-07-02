package com.school.management.service;

import java.util.List;

import com.school.management.dto.ReportCardDto;
import com.school.management.model.ReportCard;

public interface ReportCardService {
    ReportCard createReportCard(ReportCardDto reportCardDto);

	ReportCard getReportCardById(Long id);

    List<ReportCard> getReportCardByStudentId(Long studentId);

	ReportCard updateReportCard(Long id, ReportCardDto reportCardDto);

	void deleteReportCard(Long id);

	List<ReportCard> getReportCard();  
}
