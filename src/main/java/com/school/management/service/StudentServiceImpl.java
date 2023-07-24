package com.school.management.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.RoleDto;
import com.school.management.dto.StudentDTO;
import com.school.management.dto.UserDto;
import com.school.management.model.Classes;
import com.school.management.model.Student;
import com.school.management.model.User;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.UserRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Override
    public StudentDTO GetStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        StudentDTO model = modelMapper.map(student, StudentDTO.class);
        return model;
    }

    @Override
    public StudentDTO UpdateProfile(StudentDTO student) {
        Student newStudent = studentRepository.findByEmail(student.getEmail());

        if (newStudent == null) {
            throw new StudentException("Student " + student.getEmail() + " cant be found");
        }
        Classes classes = classesRepository.findByName(student.getClassName());
        List<Classes> classList = new ArrayList<Classes>();
        classList.add(classes);
        User user = userRepository.findByEmail(newStudent.getUser().getEmail()).get();
        if(user !=null){
            user.setEmail(student.getEmail());
            userRepository.save(user);
        }
        // newStudent.setAddress(student.getAddress()).setClassName(classs).setDob(student.getDob()).setEmail(student.getEmail()).setGender(student.getGender()).setImage(student.getImage()).setName(student.getName()).setPhone(student.getPhone()).setStatus(student.getStatus());
        Student saveStudent = modelMapper.map(student, Student.class).setId(newStudent.getId());
        studentRepository.save(saveStudent);

        return student;
    }

    @Override
    public List<StudentDTO> GetAllStudent() {
        List<StudentDTO> students = new ArrayList<>();
        studentRepository.findAll().stream()
                .forEach(student -> students.add(modelMapper.map(student, StudentDTO.class)));
        return students;
    }

    @Override
    public String DeleteStudent(Long email) {
        Student deleteStudent = studentRepository.findById(email).get();

        if (deleteStudent == null) {
            throw new StudentException("Student " + email + " cant be found");

        }
        studentRepository.delete(deleteStudent);
        return "Delete Successfully";
    }

    @Override
    public StudentDTO AddStudent(StudentDTO student) {
        Student existStudent = studentRepository.findByEmail(student.getEmail());
        if (existStudent != null) {
            throw new StudentException("Student already exists, "+existStudent.getName()+" "+existStudent.getEmail());
        }

        Student newStudent = modelMapper.map(student, Student.class).setStatus("pending");
        studentRepository.save(newStudent.setClassName(null));
        return student;
    }

    public String upgradeClass(String className, String email) throws StudentException {
        try {
            Classes classes = classesRepository.findByName(className);
            // Classes classes = new Classes(0,className,)
            Student student = studentRepository.findByEmail(email);
            List<Classes> classList = new ArrayList<Classes>();
            classList.add(classes);
            student.setClassName(classList);
            studentRepository.save(student);
            return null;
        } catch (Exception e) {
            throw new StudentException("upgrade error, " + e.getMessage());
        }
    }

    public class StudentException extends RuntimeException {
        public StudentException(String message) {
            super(message);
        }
    }

    @Override
    public String ConfirmStudent(StudentDTO studentDTO) {
        Student student = studentRepository.findByEmail(studentDTO.getEmail());
        StudentDTO confirmStudent = modelMapper.map(student, StudentDTO.class);
        confirmStudent.setId(null).setImage(null).setAddress(null).setStatus(null).setClassName(null);
        // if(studentDTO.getDob().equals(confirmStudent.getDob())){
        // confirmStudent.setDob(studentDTO.getDob());
        // }
        if (studentDTO.getEmail().equals(confirmStudent.getEmail())
                && studentDTO.getGender().equals(confirmStudent.getGender())
                && studentDTO.getName().equals(confirmStudent.getName())
                && studentDTO.getPhone().equals(confirmStudent.getPhone())) {
            char[] possibleCharacters = (new String(
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"))
                    .toCharArray();
            String randomStr = RandomStringUtils.random(6, 0, possibleCharacters.length - 1, false, false,
                    possibleCharacters, new SecureRandom());

            userService.signup(new UserDto(student.getEmail(), randomStr, new RoleDto("STUDENT")));
            User user = userRepository.findByEmail(student.getEmail()).get();
            student.setUser(user);
            student.setStatus("active");
            student.setImage("student.png");
            studentRepository.save(student);
            try {
                emailService.sendUsernamePassword(user.getEmail(), randomStr);
            } catch (Exception e) {
                return e.getMessage();
            }

            return "Confirmed Successfully";
        } else {
            throw new StudentException(
                    "Wrong information, if the system wrong please mail to longpntts2109002@fpt.edu.vn");
        }

    }

    // huyen
    @Override
    public List<Classes> getAllClassesByStudentId(Long studentId) {
        return classesRepository.findAllByStudentsId(studentId);
    }

    // huyen
    @Override
    public List<Student> getStudentsByClassId(Long classId) {
        return studentRepository.findAllByClassNameId(classId);
    }

    // huyen
    @Override
    public Student getStudentsById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy điểm với id: " + studentId));
    }

    // duy
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

}
