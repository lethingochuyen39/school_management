package com.school.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.management.dto.SubjectDto;
import com.school.management.model.Subject;
import com.school.management.model.Teacher;
import com.school.management.repository.SubjectRepository;
import com.school.management.repository.TeacherRepository;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Subject createSubject(SubjectDto subjectDto) {
        Subject subject = new Subject();
        subject.setName(subjectDto.getName());

        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Long id, SubjectDto subjectDto) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));

        existingSubject.setName(subjectDto.getName());

        return subjectRepository.save(existingSubject);
    }

    @Override
    public boolean deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));

        subjectRepository.delete(subject);
        return true;
    }

    @Override
    public List<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + id));
    }

    @Override
    public List<Subject> getSubjectByName(String name) {
        return subjectRepository.findByNameContainingIgnoreCase(name);
    }

    public class SubjectNotFoundException extends RuntimeException {
        public SubjectNotFoundException(String message) {
            super(message);
        }
    }

    @Override
    public void addTeacherToSubject(Long subjectId, Long teacherId) {
        // Lấy đối tượng môn học từ cơ sở dữ liệu bằng ID của môn học
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Không tìm thấy môn học với ID: " + subjectId));

        // Lấy đối tượng giáo viên từ cơ sở dữ liệu bằng ID của giáo viên
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new SubjectNotFoundException("Không tìm thấy giáo viên với ID: " + teacherId));

        // Thêm giáo viên vào môn học
        subject.getTeachers().add(teacher);
        subjectRepository.save(subject);
    }

    @Override
    public void deleteTeacherFromSubject(Long subjectId, Long teacherId) {
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);

        if (subjectOptional.isPresent() && teacherOptional.isPresent()) {
            Subject subject = subjectOptional.get();
            Teacher teacher = teacherOptional.get();

            subject.removeTeacher(teacher); // Implement a method in Subject entity to remove the teacher from the list
                                            // of teachers associated with the subject.

            // Optionally, you can save the changes to the subject entity in the database.
            // subjectRepository.save(subject);
        } else {
            throw new SubjectNotFoundException("Subject or Teacher not found.");
        }
    }

}
