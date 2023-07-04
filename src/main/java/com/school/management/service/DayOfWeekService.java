package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.model.DayOfWeek;
import com.school.management.repository.DayOfWeekRepository;

@Service
public class DayOfWeekService {
	@Autowired
	private DayOfWeekRepository dayOfWeekRepository;

	public DayOfWeek addDayOfWeek(DayOfWeek dayOfWeek) {
		return dayOfWeekRepository.save(dayOfWeek);
	}

	public List<DayOfWeek> getAllDayOfWeeks() {
		return dayOfWeekRepository.findAll();
	}
}
