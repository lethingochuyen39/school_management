package com.school.management.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.RoleDto;
import com.school.management.dto.UserDto;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.repository.TeacherRepository;
import com.school.management.repository.UserRepository;

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
        if (teacher.getName() == null || teacher.getGender() == null ||
                teacher.getDob() == null
                || teacher.getEmail() == null || teacher.getAddress() == null ||
                teacher.getPhone() == null
                || teacher.getStatus() == null) {
            throw new IllegalArgumentException("Name, description, value, and year are required.");
        }

        if (teacherRepository.existsByEmail(teacher.getEmail()) && teacherRepository.existsByPhone(teacher.getPhone())) {
            throw new IllegalArgumentException("This email and phone have already used.");

        }

        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new IllegalArgumentException("This email have already used.");

        }

        if (teacherRepository.existsByPhone(teacher.getPhone())) {
            throw new IllegalArgumentException("This phone have already used.");
        }

        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacher) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Metric not found with id: " + id);
        }

        if (teacher.getName() == null || teacher.getGender() == null ||
                teacher.getDob() == null
                || teacher.getEmail() == null || teacher.getAddress() == null ||
                teacher.getPhone() == null
                || teacher.getStatus() == null) {
            throw new IllegalArgumentException("Name, description, value, and year are required.");
        }

        if (teacherRepository.existsByEmail(teacher.getEmail()) && teacherRepository.existsByPhone(teacher.getPhone())) {
            throw new IllegalArgumentException("This email and phone have already used.");

        }

        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new IllegalArgumentException("This email have already used.");

        }

        if (teacherRepository.existsByPhone(teacher.getPhone())) {
            throw new IllegalArgumentException("This phone have already used.");
        }

        teacher.setId(id);
        return teacherRepository.save(teacher);
    }

    @Override
    public boolean deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Metric not found with id: " + id);
        }
        teacherRepository.deleteById(id);
        return true;
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


    @Override
    public Long generateAccount() {
        Long totalRowInStudent = teacherRepository.count();
        List <Teacher> list = teacherRepository.findByUser(null);
        // list.stream().forEach(student -> studentRepository.save(studentService.GiveAccessAccount(student.getEmail(),student)));
        list.stream().forEach(teacher -> {
            char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();
            String randomStr = RandomStringUtils.random( 6, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
// System.out.println( randomStr );
            UserDto userDto = userService.signup(new UserDto(teacher.getEmail(), randomStr, new RoleDto("TEACHER")));
            Optional<User> user = userRepository.findByEmail(userDto.getEmail());
            if (!user.isPresent()){
                throw new TeacherNotFoundException("User not found: " + userDto.getEmail());
            }
            teacherRepository.save(teacher.setUser(user.get()));
        });
        return totalRowInStudent;
    }

    // private static final String UPLOAD_FOLDER = "uploads/teachers/";

    // @Autowired
    // private TeacherRepository teacherRepository;

    // @Override
    // public Teacher createTeacher(Teacher teacher, MultipartFile file, Long
    // uploadedById) {
    // validateTeacher(teacher); // Validate teacher fields

    // String fileName = file.getOriginalFilename();
    // String filePath = saveFile(file);

    // teacher.setFilePath(filePath);
    // teacher.setFileName(fileName);
    // return teacherRepository.save(teacher);
    // }

    // @Override
    // public Teacher updateTeacher(Long id, Teacher teacher, MultipartFile file) {
    // if (!teacherRepository.existsById(id)) {
    // throw new TeacherNotFoundException("Teacher not found with id: " + id);
    // }

    // validateTeacher(teacher); // Validate teacher fields

    // teacher.setId(id);

    // String fileName = file.getOriginalFilename();
    // String filePath = saveFile(file);

    // teacher.setFilePath(filePath);
    // teacher.setFileName(fileName);

    // return teacherRepository.save(teacher);
    // }

    // private String saveFile(MultipartFile file) {
    // try {
    // Path uploadPath = Path.of(UPLOAD_FOLDER).toAbsolutePath().normalize();

    // if (!Files.exists(uploadPath)) {
    // Files.createDirectories(uploadPath);
    // }

    // String fileName = file.getOriginalFilename();
    // String filePath = uploadPath + "/" + fileName;

    // Files.copy(file.getInputStream(), Path.of(filePath),
    // StandardCopyOption.REPLACE_EXISTING);

    // return filePath;
    // } catch (IOException e) {
    // throw new RuntimeException("Failed to save file.");
    // }
    // }

}
