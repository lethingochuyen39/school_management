package com.school.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.ScheduleDto;
import com.school.management.model.Classes;
import com.school.management.model.Schedule;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ClassesRepository classesRepository;

	@Override
	public ScheduleDto creaSchedule(ScheduleDto scheduleDto) {

		if (scheduleDto.getSemester() == null ||
				scheduleDto.getClassesId() == null) {
			throw new IllegalArgumentException("Học kỳ, lớp và năm học là bắt buộc.");
		}

		Long classId = scheduleDto.getClassesId();

		Classes classes = classesRepository.findById(classId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lớp với id: " + classId));
		// Kiểm tra xem lớp học đã có thời khóa biểu hay chưa
		boolean hasSchedule = classesRepository.hasSchedule(classId);
		if (hasSchedule) {
			throw new IllegalArgumentException("Lớp học đã có thời khóa biểu.");
		}
		// Gán thông tin thời gian của năm học vào DTO
		scheduleDto.setStartDate(classes.getAcademicYear().getStartDate());
		scheduleDto.setEndDate(classes.getAcademicYear().getEndDate());

		// Kiểm tra xem thời khóa biểu của học kỳ đã tồn tại hay chưa
		if (scheduleRepository.existsBySemesterAndClassesId(scheduleDto.getSemester(), scheduleDto.getClassesId())) {
			throw new IllegalArgumentException("Thời khóa biểu cho học kỳ và lớp đã tồn tại.");
		}

		// Kiểm tra xem ngày bắt đầu có nhỏ hơn ngày kết thúc không
		if (scheduleDto.getStartDate().isAfter(scheduleDto.getEndDate())) {
			throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
		}

		Schedule schedule = new Schedule();
		schedule.setClasses(classes)
				.setSemester(scheduleDto.getSemester())
				.setStartDate(scheduleDto.getStartDate())
				.setEndDate(scheduleDto.getEndDate());
		scheduleRepository.save(schedule);

		// Sao chép thông tin từ đối tượng Schedule sang ScheduleDto
		scheduleDto.setId(schedule.getId());
		scheduleDto.setClassesId(schedule.getClasses().getId());
		scheduleDto.setSemester(schedule.getSemester());
		scheduleDto.setStartDate(schedule.getStartDate());
		scheduleDto.setEndDate(schedule.getEndDate());

		return scheduleDto;
	}

	@Override
	public Schedule getScheduleById(long id) {
		return scheduleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thời khóa biểu với id: " + id));
	}

	@Override
	public List<Schedule> getAllSchedules() {

		return scheduleRepository.findAll();
	}

	@Override
	public void deleteSchedule(long id) {
		if (!scheduleRepository.existsById(id)) {
			throw new IllegalArgumentException("không tìm thấy thời khóa biểu với id: " + id);
		}
		scheduleRepository.deleteById(id);
	}

	@Override
	public ScheduleDto updateSchedule(Long scheduleId, ScheduleDto scheduleDto) {
		// ui sẽ ko update schedule
		if (scheduleDto.getSemester() == null || scheduleDto.getClassesId() == null) {
			throw new IllegalArgumentException("Học kỳ, lớp và năm học là bắt buộc.");
		}
		// Lấy thông tin lớp cập nhật
		Long classId = scheduleDto.getClassesId();
		Classes classes = classesRepository.findById(classId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lớp với id: " + classId));
		// Lấy thông tin thời khóa biểu cần cập nhật
		Schedule existingSchedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thời khóa biểu với id: " + scheduleId));
		if (!existingSchedule.getClasses().getId().equals(classId)) {
			throw new IllegalArgumentException("Lớp không khớp với thời khóa biểu đã tồn tại.");
		}
		if (!existingSchedule.getSemester().equals(scheduleDto.getSemester())) {
			throw new IllegalArgumentException("Học kỳ không thể thay đổi.");
		}
		existingSchedule.setClasses(classes)
				.setSemester(scheduleDto.getSemester())
				.setStartDate(scheduleDto.getStartDate())
				.setEndDate(scheduleDto.getEndDate());
		scheduleRepository.save(existingSchedule);

		// Sao chép thông tin từ đối tượng Schedule sang ScheduleDto
		scheduleDto.setId(existingSchedule.getId());
		scheduleDto.setClassesId(classes.getId());
		scheduleDto.setSemester(existingSchedule.getSemester());
		scheduleDto.setStartDate(existingSchedule.getStartDate());
		scheduleDto.setEndDate(existingSchedule.getEndDate());

		return scheduleDto;
	}

	@Override
	public List<ScheduleDto> getSchedulesByClassName(String className) {
		List<Schedule> schedules = scheduleRepository.findByClassesNameContainingIgnoreCase(className);
		return convertToDtoList(schedules);
	}

	private List<ScheduleDto> convertToDtoList(List<Schedule> schedules) {
		List<ScheduleDto> scheduleDtos = new ArrayList<>();
		for (Schedule schedule : schedules) {
			ScheduleDto scheduleDto = new ScheduleDto();
			scheduleDto.setId(schedule.getId())
					.setClassesId(schedule.getClasses().getId())
					.setSemester(schedule.getSemester())
					.setStartDate(schedule.getStartDate())
					.setEndDate(schedule.getEndDate());

			scheduleDtos.add(scheduleDto);
		}
		return scheduleDtos;
	}

}
