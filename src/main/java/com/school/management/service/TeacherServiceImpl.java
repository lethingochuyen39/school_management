package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.TeacherDto;
import com.school.management.model.Teacher;
import com.school.management.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    // @Autowired
    // private UserService userService;

    // @Autowired
    // private UserRepository userRepository;

    @Override
    public Teacher createTeacher(TeacherDto teacherDto) {
        // Long userId = teacherDto.getUserId();

        // User user = userRepository.findById(userId)
        // .orElseThrow(() -> new IllegalArgumentException("User not found with id: " +
        // userId));

        Teacher teacher = new Teacher();
        teacher.setName(teacherDto.getName());
        teacher.setGender(teacherDto.getGender());
        teacher.setDob(teacherDto.getDob());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setAddress(teacherDto.getAddress());
        teacher.setPhone(teacherDto.getPhone());
        teacher.setStatus(teacherDto.getStatus());
        // teacher.setUser(user);

        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, TeacherDto teacherDto) {

        // Long userId = teacherDto.getUserId();

        // User user = userRepository.findById(userId)
        // .orElseThrow(() -> new IllegalArgumentException("User not found with id: " +
        // userId));

        Teacher exitingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + id));

        exitingTeacher.setName(teacherDto.getName());
        exitingTeacher.setGender(teacherDto.getGender());
        exitingTeacher.setDob(teacherDto.getDob());
        exitingTeacher.setEmail(teacherDto.getEmail());
        exitingTeacher.setAddress(teacherDto.getAddress());
        exitingTeacher.setPhone(teacherDto.getPhone());
        exitingTeacher.setStatus(teacherDto.getStatus());
        // exitingTeacher.setUser(user);

        return teacherRepository.save(exitingTeacher);
    }

    @Override
    public boolean deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
        return true;
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Không tìm thấy giáo viên với id: " + id));
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Teacher> getTeacherByName(String name) {
        return teacherRepository.findByNameContainingIgnoreCase(name);
    }

    public class TeacherNotFoundException extends RuntimeException {
        public TeacherNotFoundException(String message) {
            super(message);
        }
    }
}
