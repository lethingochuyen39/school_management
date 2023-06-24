package com.school.management.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.RoleDto;
import com.school.management.dto.UserDto;
import com.school.management.model.Student;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.repository.TeacherRepository;
import com.school.management.repository.UserRepository;
import com.school.management.service.StudentServiceImpl.StudentException;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Teacher createTeacher(Teacher teacher) {
        if (teacher.getName() == null || teacher.getGender() == null || teacher.getDob() == null
                || teacher.getEmail() == null || teacher.getAddress() == null || teacher.getPhone() == null
                || teacher.getStatus() == null || teacher.getImage() == null || teacher.getUser() == null) {
            throw new IllegalArgumentException(
                    "Name, Gender, Dob, Email, Address, Phone, Status, Images are required.");
        }
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacher) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Teacher not found with id: " + id);
        }

        if (teacher.getName() == null || teacher.getGender() == null || teacher.getDob() == null
                || teacher.getEmail() == null || teacher.getAddress() == null || teacher.getPhone() == null
                || teacher.getStatus() == null || teacher.getImage() == null || teacher.getUser() == null) {
            throw new IllegalArgumentException("Subject and Teacher are required.");
        }
        teacher.setId(id);
        return teacherRepository.save(teacher);
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
    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Class not found with id: " + id));
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

    @Override
    public Long generateAccount() {
        Long totalRowInStudent = teacherRepository.count();
        List <Teacher> list = teacherRepository.findByUser(null);
        // list.stream().forEach(student -> studentRepository.save(studentService.GiveAccessAccount(student.getEmail(),student)));
        list.stream().forEach(student -> {
            char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();
            String randomStr = RandomStringUtils.random( 6, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
// System.out.println( randomStr );
            UserDto userDto = userService.signup(new UserDto(student.getEmail(), randomStr, new RoleDto("STUDENT")));
            Optional<User> user = userRepository.findByEmail(userDto.getEmail());
            if (!user.isPresent()){
                throw new TeacherNotFoundException("User not found: " + userDto.getEmail());
            }
            teacherRepository.save(student.setUser(user.get()));
        });
        return totalRowInStudent;
    }
}
