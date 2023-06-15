package com.school.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.scheduleDto.ScheduleDetailDto;
import com.school.management.dto.scheduleDto.ScheduleDto;
import com.school.management.dto.scheduleDto.SubjectDto;
import com.school.management.dto.scheduleDto.TeacherDto;
import com.school.management.model.Classes;
import com.school.management.model.Schedule;
import com.school.management.model.ScheduleDetail;
import com.school.management.model.Subject;
import com.school.management.model.Teacher;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.ScheduleDetailRepository;
import com.school.management.repository.ScheduleRepository;
import com.school.management.repository.SubjectRepository;
import com.school.management.repository.TeacherRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ClassesRepository classesRepository;

	@Autowired
	private ScheduleDetailRepository scheduleDetailRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	public ScheduleDto creaSchedule(ScheduleDto scheduleDto) {

		if (scheduleDto.getSemester() == null ||
				scheduleDto.getClassesId() == null) {
			throw new IllegalArgumentException("Học kỳ, lớp là bắt buộc.");
		}

		Long classId = scheduleDto.getClassesId();

		Classes classes = classesRepository.findById(classId)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lớp với id: " + classId));
		// Kiểm tra xem lớp học đã có thời khóa biểu hay chưa
		boolean hasSchedule = classesRepository.hasSchedule(classId, scheduleDto.getSemester());
		if (hasSchedule) {
			throw new IllegalArgumentException(
					"Lớp học đã có thời khóa biểu cho học kỳ " + scheduleDto.getSemester() + ".");
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

		// List<ScheduleDetailDto> scheduleDetailDtos =
		// scheduleDto.getScheduleDetails();
		// for (ScheduleDetailDto scheduleDetailDto : scheduleDetailDtos) {
		// String dayOfWeek = scheduleDetailDto.getDayOfWeek();
		// Integer lesson = scheduleDetailDto.getLesson();
		// SubjectDto subject = scheduleDetailDto.getSubject();
		// TeacherDto teacher = scheduleDetailDto.getTeacher();
		// ScheduleDetail scheduleDetail = new ScheduleDetail();
		// scheduleDetail.setDayOfWeek(dayOfWeek)
		// .setLesson(lesson)
		// .setSubject(subjectRepository.findById(subject.getId())
		// .orElseThrow(() -> new IllegalArgumentException(
		// "Không tìm thấy môn học với id: " + subject.getId())))
		// .setTeacher(teacherRepository.findById(teacher.getId())
		// .orElseThrow(() -> new IllegalArgumentException(
		// "Không tìm thấy giáo viên với id: " + teacher.getId())))
		// .setSchedule(schedule);
		// scheduleDetailRepository.save(scheduleDetail);
		// }

		// Sao chép thông tin từ đối tượng Schedule sang ScheduleDto
		scheduleDto.setId(schedule.getId());
		scheduleDto.setClassesId(schedule.getClasses().getId());
		scheduleDto.setSemester(schedule.getSemester());
		scheduleDto.setStartDate(schedule.getStartDate());
		scheduleDto.setEndDate(schedule.getEndDate());

		// Lấy danh sách ScheduleDetail từ cơ sở dữ liệu và gán vào ScheduleDto
		List<ScheduleDetail> scheduleDetails = scheduleDetailRepository.findBySchedule(schedule);
		List<ScheduleDetailDto> scheduleDetailDtoss = convertToScheduleDetailDtoList(scheduleDetails);
		scheduleDto.setScheduleDetails(scheduleDetailDtoss);

		return scheduleDto;
	}

	private List<ScheduleDetailDto> convertToScheduleDetailDtoList(List<ScheduleDetail> scheduleDetails) {
		List<ScheduleDetailDto> scheduleDetailDtos = new ArrayList<>();
		for (ScheduleDetail scheduleDetail : scheduleDetails) {
			ScheduleDetailDto scheduleDetailDto = new ScheduleDetailDto();
			// Sao chép thông tin từ đối tượng ScheduleDetail sang ScheduleDetailDto
			scheduleDetailDto.setDayOfWeek(scheduleDetail.getDayOfWeek());
			scheduleDetailDto.setLesson(scheduleDetail.getLesson());
			// Lấy thông tin môn học và giáo viên từ cơ sở dữ liệu và gán vào
			// ScheduleDetailDto
			Subject subject = subjectRepository.findById(scheduleDetail.getSubject().getId())
					.orElseThrow(() -> new IllegalArgumentException(
							"Không tìm thấy môn học với id: " + scheduleDetail.getSubject().getId()));

			SubjectDto subjectDto = convertToSubjectDto(subject);
			scheduleDetailDto.setSubject(subjectDto);
			scheduleDetailDto
					.setTeacher(convertToTeacherDto(teacherRepository.findById(scheduleDetail.getTeacher().getId())
							.orElseThrow(() -> new IllegalArgumentException(
									"Không tìm thấy giáo viên với id: " + scheduleDetail.getTeacher().getId()))));
			scheduleDetailDtos.add(scheduleDetailDto);
		}
		return scheduleDetailDtos;
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
			throw new IllegalArgumentException("Học kỳ, lớp là bắt buộc.");
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
	public List<Schedule> getSchedulesByClassName(String className) {
		List<Schedule> schedules = scheduleRepository.findByClassesNameContainingIgnoreCase(className);
		return schedules;
	}

	private SubjectDto convertToSubjectDto(Subject subject) {
		SubjectDto subjectDto = new SubjectDto();
		// Sao chép thông tin từ đối tượng Subject sang SubjectDto
		subjectDto.setId(subject.getId());
		subjectDto.setName(subject.getName());
		// ...

		return subjectDto;
	}

	private TeacherDto convertToTeacherDto(Teacher teacher) {
		TeacherDto teacherDto = new TeacherDto();
		// Sao chép thông tin từ đối tượng Teacher sang TeacherDto
		teacherDto.setId(teacher.getId());
		teacherDto.setName(teacher.getName());
		// ..
		return teacherDto;
	}
}
