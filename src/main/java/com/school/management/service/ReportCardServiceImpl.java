package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.ReportCard;
import com.school.management.repository.ReportCardRepository;

@Service
public class ReportCardServiceImpl implements ReportCardService{
    @Autowired ReportCardRepository reportCardRepository;

    @Override
    public ReportCard createReportCard(ReportCard reportCard) {
        if (reportCard.getStudent() == null || reportCard.getAverageScore() == null 
            || reportCard.getAcademicYear() == null) {
			throw new IllegalArgumentException("Student , Average Score and Academic Year are required.");
		}

		if (reportCardRepository.existsById(reportCard.getId())) {
			throw new IllegalArgumentException("Report Card with the same id already exists.");
		}

		return reportCardRepository.save(reportCard);
    }

    @Override
    public ReportCard getReportCardById(Long id) {
        return reportCardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Report Card not found with id: " + id));
    }

    @Override
    public List<ReportCard> getReportCardByStudentId(Long studentId) {
        return reportCardRepository.findByStudentId(studentId);
    }
    @Override
    public ReportCard updateReportCard(Long id, ReportCard reportCard) {
        if (!reportCardRepository.existsById(id)) {
            throw new ReportCardNotFoundException("Report Card not found with id: " + id);
        }

        if (reportCard.getStudent() == null || reportCard.getAverageScore() == null 
            || reportCard.getAcademicYear() == null) {
			throw new IllegalArgumentException("Student , Average Score and Academic Year are required.");
		}

        ReportCard existingReportCard = reportCardRepository.findById(id)
                .orElseThrow(() -> new ReportCardNotFoundException("Report Card not found with id: " + id));

        if (existingReportCard != null && !existingReportCard.getId().equals(reportCard.getId())) {
            if (reportCardRepository.existsById(reportCard.getId())) {
                throw new IllegalArgumentException("Report Card is already exists.");
            }
        }
        reportCard.setId(id);
        return reportCardRepository.save(reportCard);
    }

    @Override
    public void deleteReportCard(Long id) {
        if (!reportCardRepository.existsById(id)) {
			throw new IllegalArgumentException("Report Card not found with id: " + id);
		}
		reportCardRepository.deleteById(id);
    }

    @Override
    public List<ReportCard> getReportCard() {
        return reportCardRepository.findAll();
    }

    public class ReportCardNotFoundException extends RuntimeException {
        public ReportCardNotFoundException(String message) {
            super(message);
        }
    }
}
