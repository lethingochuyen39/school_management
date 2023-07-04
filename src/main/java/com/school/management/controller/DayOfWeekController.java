package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.model.DayOfWeek;
import com.school.management.service.DayOfWeekService;

@RestController
@RequestMapping("/api/dayofweek")
public class DayOfWeekController {

    @Autowired
    private DayOfWeekService dayOfWeekService;

    @PostMapping
    public ResponseEntity<DayOfWeek> addDayOfWeek(@RequestBody DayOfWeek dayOfWeek) {
        DayOfWeek addedDayOfWeek = dayOfWeekService.addDayOfWeek(dayOfWeek);
        return ResponseEntity.ok(addedDayOfWeek);
    }

    @GetMapping
    public ResponseEntity<List<DayOfWeek>> getAllDayOfWeeks() {
        List<DayOfWeek> dayOfWeeks = dayOfWeekService.getAllDayOfWeeks();
        return ResponseEntity.ok(dayOfWeeks);
    }
}
