package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.school.management.dto.ReportCardDto;
import com.school.management.model.ReportCard;
import com.school.management.service.ReportCardServiceImpl;

@RestController
@RequestMapping("/api/report_cards")
public class ReportCardController {
    @Autowired
    private ReportCardServiceImpl ReportCardServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createReportCard(@RequestBody ReportCardDto reportCardDto) {
        try {
            ReportCard createdReportCard = ReportCardServiceImpl.createReportCard(reportCardDto);
            return ResponseEntity.ok().body(createdReportCard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findById/{id}")
	public ResponseEntity<?> getReportCardById(@PathVariable Long id) {
		try {
			ReportCard evaluationRecord = ReportCardServiceImpl.getReportCardById(id);
			return ResponseEntity.ok(evaluationRecord);
		} catch (ReportCardServiceImpl.ReportCardNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateReportCard(@PathVariable Long id,
			@RequestBody ReportCardDto reportCardDto) {
		try {
			ReportCard updatedReportCards = ReportCardServiceImpl.updateReportCard(id, reportCardDto);
			return ResponseEntity.ok(updatedReportCards);
		} catch (ReportCardServiceImpl.ReportCardNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteReportCard(@PathVariable Long id) {
		try {
			ReportCardServiceImpl.deleteReportCard(id);
			return ResponseEntity.ok().build();
		} catch (ReportCardServiceImpl.ReportCardNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<ReportCard>> getReportCard() {
		List<ReportCard> reportCard = ReportCardServiceImpl.getReportCard();
		return ResponseEntity.ok(reportCard);
	}

    @GetMapping("/search/studentId")
	public ResponseEntity<List<ReportCard>> searchScoresByStudentId(@RequestParam("id") Long studentId) {
		List<ReportCard> reportCard = ReportCardServiceImpl.getReportCardByStudentId(studentId);
		return ResponseEntity.ok(reportCard);
	}
}
