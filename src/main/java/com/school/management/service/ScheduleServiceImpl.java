package com.school.management.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.scheduleDto.ScheduleDto;
import com.school.management.model.Classes;
import com.school.management.model.DayOfWeek;
import com.school.management.model.Lesson;
import com.school.management.model.Schedule;
import com.school.management.model.ScheduleStatus;
import com.school.management.model.Subject;
import com.school.management.model.Teacher;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.DayOfWeekRepository;
import com.school.management.repository.LessonRepository;
import com.school.management.repository.ScheduleRepository;
import com.school.management.repository.SubjectRepository;
import com.school.management.repository.TeacherRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private DayOfWeekRepository dayOfWeekRepository;

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private ClassesRepository classesRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	public Schedule addSchedule(ScheduleDto scheduleDTO) {

		if (scheduleDTO.getDayOfWeekId() == null || scheduleDTO.getLessonId() == null ||
				scheduleDTO.getTeacherId() == null || scheduleDTO.getClassId() == null
				|| scheduleDTO.getSubjectId() == null) {
			throw new IllegalArgumentException("Tất cả các trường là bắt buộc");
		}
		// Xử lý lỗi khóa ngoại
		DayOfWeek dayOfWeek = dayOfWeekRepository.findById(scheduleDTO.getDayOfWeekId())
				.orElseThrow(() -> new IllegalArgumentException("Khóa ngoại không hợp lệ: dayOfWeekId"));
		Lesson lesson = lessonRepository.findById(scheduleDTO.getLessonId())
				.orElseThrow(() -> new IllegalArgumentException("Khóa ngoại không hợp lệ: lessonId"));
		Subject subject = subjectRepository.findById(scheduleDTO.getSubjectId())
				.orElseThrow(() -> new IllegalArgumentException("Khóa ngoại không hợp lệ: subjectId"));
		Classes classes = classesRepository.findById(scheduleDTO.getClassId())
				.orElseThrow(() -> new IllegalArgumentException("Khóa ngoại không hợp lệ: classId"));
		Teacher teacher = teacherRepository.findById(scheduleDTO.getTeacherId())
				.orElseThrow(() -> new IllegalArgumentException("Khóa ngoại không hợp lệ: teacherId"));

		// Kiểm tra xem thời khóa biểu đã tồn tại và có trạng thái "inactive" hay không
		ScheduleStatus statusInActive = ScheduleStatus.InActive;

		boolean isScheduleExist = scheduleRepository
				.existsByDayOfWeekIdAndLessonIdAndSubjectIdAndTeacherIdAndClassesIdAndStatus(
						scheduleDTO.getDayOfWeekId(), scheduleDTO.getLessonId(),
						scheduleDTO.getSubjectId(), scheduleDTO.getTeacherId(), scheduleDTO.getClassId(),
						statusInActive);
		if (isScheduleExist) {
			throw new IllegalArgumentException("Thời khóa biểu đã tồn tại và đang ở trạng thái ẩn hoạt động");
		}
		// Kiểm tra lớp học đã có lịch học vào tiết và ngày học đã chọn hay chưa
		boolean isClassBusy = scheduleRepository.existsByClassesIdAndDayOfWeekIdAndLessonId(
				scheduleDTO.getClassId(), scheduleDTO.getDayOfWeekId(), scheduleDTO.getLessonId());

		if (isClassBusy) {
			throw new IllegalArgumentException("Lớp học đã có lịch dạy vào thời điểm và tiết học đã chọn");
		}

		// Kiểm tra xem thời khóa biểu đã tồn tại hay chưa
		boolean isScheduleExists = scheduleRepository
				.existsByDayOfWeekIdAndLessonIdAndSubjectIdAndTeacherIdAndClassesId(
						scheduleDTO.getDayOfWeekId(), scheduleDTO.getLessonId(),
						scheduleDTO.getSubjectId(), scheduleDTO.getTeacherId(), scheduleDTO.getClassId());

		if (isScheduleExists) {
			throw new IllegalArgumentException("Thời khóa biểu đã tồn tại trong lớp");
		}

		// Kiểm tra xem giáo viên đã có lịch dạy vào tiết học và buổi học đã chọn hay
		// chưa
		boolean isTeacherBusy = scheduleRepository.existsByTeacherIdAndDayOfWeekIdAndLessonIdAndStatus(
				scheduleDTO.getTeacherId(), scheduleDTO.getDayOfWeekId(), scheduleDTO.getLessonId(),
				ScheduleStatus.Active);
		if (isTeacherBusy) {
			throw new IllegalArgumentException("Giáo viên đã có lịch dạy vào thời điểm và tiết học đã chọn");
		}

		// Chuyển đổi ScheduleDTO thành đối tượng Schedule
		Schedule schedule = new Schedule();
		schedule.setDayOfWeek(dayOfWeek);
		schedule.setLesson(lesson);
		schedule.setSubject(subject);
		schedule.setClasses(classes);
		schedule.setTeacher(teacher);
		schedule.setStatus(ScheduleStatus.Active);

		// Thêm thời khóa biểu vào cơ sở dữ liệu
		return scheduleRepository.save(schedule);
	}

	@Override
	public List<Schedule> getAllSchedules() {
		return scheduleRepository.findAll();
	}

	@Override
	public List<Schedule> getSchedulesByClass(Long classId) {
		return scheduleRepository.findByClassesId(classId);
	}

	@Override
	public Schedule updateScheduleStatus(Long scheduleId, ScheduleStatus status) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new IllegalArgumentException("Thời khóa biểu không tồn tại"));

		// Kiểm tra nếu giáo viên, môn học, tiết và thứ đã được cung cấp
		if (schedule.getTeacher() != null && schedule.getSubject() != null && schedule.getLesson() != null
				&& schedule.getDayOfWeek() != null && schedule.getClasses() != null) {

			// Lấy danh sách các thời khóa biểu có cùng tiết, thứ, giáo viên và môn học
			List<Schedule> conflictingSchedules = scheduleRepository.findByLessonAndDayOfWeekAndTeacherAndSubject(
					schedule.getLesson(), schedule.getDayOfWeek(), schedule.getTeacher(), schedule.getSubject());

			// Lọc ra các thời khóa biểu đang ở trạng thái active của các lớp khác
			List<Schedule> activeConflictingSchedules = conflictingSchedules.stream()
					.filter(s -> s.getStatus() == ScheduleStatus.Active
							&& !s.getClasses().equals(schedule.getClasses()))
					.collect(Collectors.toList());

			// Kiểm tra nếu có ít nhất một thời khóa biểu active đang tồn tại
			if (!activeConflictingSchedules.isEmpty()) {
				// Không cho phép cập nhật trạng thái nếu đã có thời khóa biểu active tồn tại
				throw new IllegalStateException(
						"Không thể cập nhật trạng thái thời khóa biểu vì đã có thời khóa biểu active tồn tại.");
			}
		}

		schedule.setStatus(status);
		return scheduleRepository.save(schedule);
	}

	@Override
	public Schedule getScheduleById(long id) {
		return scheduleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thời khóa biểu với id: " + id));
	}

	@Override
	public List<Schedule> getSchedulesByClassName(String className) {
		List<Schedule> schedules = scheduleRepository.findByClassesNameContainingIgnoreCase(className);
		return schedules;
	}

	@Override
	public List<Schedule> searchSchedule(String className) {
		if (className == null || className.trim().isEmpty()) {
			return getAllSchedules();
		} else {
			return getSchedulesByClassName(className);
		}
	}

	@Override
	public void deleteSchedule(long scheduleId) {
		Schedule schedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new IllegalArgumentException("Thời khóa biểu không tồn tại"));

		scheduleRepository.deleteById(scheduleId);
	}

	@Override
	public Schedule updateSchedule(Long scheduleId, ScheduleDto scheduleDTO) {
		Schedule existingSchedule = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new IllegalArgumentException("Thời khóa biểu không tồn tại"));

		boolean isSubjectChanged = !Objects.equals(existingSchedule.getSubject().getId(), scheduleDTO.getSubjectId());
		boolean isTeacherChanged = !Objects.equals(existingSchedule.getTeacher().getId(), scheduleDTO.getTeacherId());

		if (!isSubjectChanged && !isTeacherChanged) {
			// Không có sự thay đổi môn học và giáo viên, không cần update
			return existingSchedule;
		}

		if (isTeacherChanged) {
			boolean isTeacherBusy = scheduleRepository.existsByTeacherIdAndDayOfWeekIdAndLessonIdAndStatus(
					scheduleDTO.getTeacherId(), existingSchedule.getDayOfWeek().getId(),
					existingSchedule.getLesson().getId(), ScheduleStatus.Active);

			if (isTeacherBusy) {
				throw new IllegalArgumentException("Giáo viên đã có lịch dạy vào thời điểm và tiết học đã chọn");
			}
		}

		if (isSubjectChanged || isTeacherChanged) {
			// Cập nhật thông tin môn học và giáo viên
			Subject subject = subjectRepository.findById(scheduleDTO.getSubjectId())
					.orElseThrow(() -> new IllegalArgumentException("Khóa ngoại không hợp lệ: subjectId"));
			Teacher teacher = teacherRepository.findById(scheduleDTO.getTeacherId())
					.orElseThrow(() -> new IllegalArgumentException("Khóa ngoại không hợp lệ: teacherId"));

			existingSchedule.setSubject(subject);
			existingSchedule.setTeacher(teacher);
		}

		return scheduleRepository.save(existingSchedule);
	}

	@Override
	public List<Schedule> getTeacherSchedule(Long teacherId) {
		return scheduleRepository.findByTeacherId(teacherId);
	}

}
