package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.scheduleDto.ScheduleDetailDto;
import com.school.management.dto.scheduleDto.SubjectDto;
import com.school.management.dto.scheduleDto.TeacherDto;
import com.school.management.model.Schedule;
import com.school.management.model.ScheduleDetail;
import com.school.management.model.Subject;
import com.school.management.model.Teacher;
import com.school.management.repository.ScheduleDetailRepository;
import com.school.management.repository.SubjectRepository;
import com.school.management.repository.TeacherRepository;

@Service
public class scheduleDetailServiceImpl implements scheduleDetailService {

	@Autowired
	private ScheduleDetailRepository scheduleDetailRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private ScheduleServiceImpl scheduleRepository;

	@Override
	public void saveScheduleDetails(Long scheduleId, List<ScheduleDetailDto> scheduleDetailDtos) {
		Schedule schedule = scheduleRepository.getScheduleById(scheduleId);
		if (schedule == null) {
			throw new IllegalArgumentException("Không tìm thấy thời khóa biểu với id: " + scheduleId);
		}

		for (ScheduleDetailDto scheduleDetailDto : scheduleDetailDtos) {
			String dayOfWeek = scheduleDetailDto.getDayOfWeek();
			Integer lesson = scheduleDetailDto.getLesson();
			SubjectDto subject = scheduleDetailDto.getSubject();
			TeacherDto teacher = scheduleDetailDto.getTeacher();
			// Kiểm tra xem subject có tồn tại trong cơ sở dữ liệu hay không
			Subject subjectEntity = subjectRepository.findById(subject.getId())
					.orElseThrow(
							() -> new IllegalArgumentException("Không tìm thấy môn học với id: " + subject.getId()));

			// Kiểm tra xem teacher có tồn tại trong cơ sở dữ liệu hay không
			Teacher teacherEntity = teacherRepository.findById(teacher.getId())
					.orElseThrow(
							() -> new IllegalArgumentException("Không tìm thấy giáo viên với id: " + teacher.getId()));

			if (scheduleDetailRepository.existsByScheduleAndDayOfWeekAndLessonAndSubjectAndTeacher(
					schedule, dayOfWeek, lesson, subjectEntity, teacherEntity)) {
				throw new IllegalArgumentException("Chi tiết thời khóa biểu đã tồn tại.");
			}

			if (scheduleDetailRepository.existsByScheduleAndDayOfWeekAndLesson(
					schedule, dayOfWeek, lesson)) {
				throw new IllegalArgumentException("Chi tiết buổi học đã tồn tại.");
			}

			ScheduleDetail scheduleDetail = new ScheduleDetail();
			scheduleDetail.setDayOfWeek(dayOfWeek)
					.setLesson(lesson)
					.setSubject(subjectEntity)
					.setTeacher(teacherEntity)
					.setSchedule(schedule);

			scheduleDetailRepository.save(scheduleDetail);
		}

	}

	@Override
	public void updateScheduleDetail(Long scheduleDetailId, ScheduleDetailDto scheduleDetailDto) {
		ScheduleDetail scheduleDetail = scheduleDetailRepository.findById(scheduleDetailId)
				.orElseThrow(() -> new IllegalArgumentException(
						"Không tìm thấy chi tiết thời khóa biểu với id: " + scheduleDetailId));

		String dayOfWeek = scheduleDetailDto.getDayOfWeek();
		Integer lesson = scheduleDetailDto.getLesson();
		SubjectDto subject = scheduleDetailDto.getSubject();
		TeacherDto teacher = scheduleDetailDto.getTeacher();

		// Kiểm tra xem dayOfWeek, lesson, subject, teacher có giá trị hợp lệ hay không
		if (dayOfWeek == null || lesson == null || subject == null || teacher == null) {
			throw new IllegalArgumentException("Thông tin chi tiết thời khóa biểu không hợp lệ: " + scheduleDetailDto);
		}

		// Kiểm tra xem subject có tồn tại trong cơ sở dữ liệu hay không
		Subject subjectEntity = subjectRepository.findById(subject.getId())
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy môn học với id: " + subject.getId()));

		// Kiểm tra xem teacher có tồn tại trong cơ sở dữ liệu hay không
		Teacher teacherEntity = teacherRepository.findById(teacher.getId())
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giáo viên với id: " + teacher.getId()));

		// Kiểm tra sự thay đổi của các trường thông tin
		boolean hasChanges = !dayOfWeek.equals(scheduleDetail.getDayOfWeek()) ||
				!lesson.equals(scheduleDetail.getLesson()) ||
				!subjectEntity.equals(scheduleDetail.getSubject()) ||
				!teacherEntity.equals(scheduleDetail.getTeacher());

		if (hasChanges) {
			// Kiểm tra sự trùng lặp của chi tiết thời khóa biểu
			if (scheduleDetailRepository.existsByScheduleAndDayOfWeekAndLessonAndSubjectAndTeacher(
					scheduleDetail.getSchedule(), dayOfWeek, lesson, subjectEntity,
					teacherEntity)) {
				throw new IllegalArgumentException("Chi tiết thời khóa biểu đã tồn tại: " + scheduleDetailDto);
			}
		}

		scheduleDetail
				.setSubject(subjectEntity)
				.setTeacher(teacherEntity);

		scheduleDetailRepository.save(scheduleDetail);

	}

	@Override
	public void deleteScheduleDetail(Long scheduleDetailId) {
		ScheduleDetail scheduleDetail = scheduleDetailRepository.findById(scheduleDetailId)
				.orElseThrow(() -> new IllegalArgumentException(
						"Không tìm thấy chi tiết thời khóa biểu với id: " + scheduleDetailId));

		scheduleDetailRepository.delete(scheduleDetail);
	}

	@Override
	public ScheduleDetail getScheduleDetailById(Long scheduleDetailId) {
		ScheduleDetail scheduleDetail = scheduleDetailRepository.findById(scheduleDetailId)
				.orElseThrow(() -> new IllegalArgumentException(
						"Không tìm thấy chi tiết thời khóa biểu với id: " + scheduleDetailId));
		return scheduleDetail;
	}

}