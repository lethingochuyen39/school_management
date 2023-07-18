package com.school.management.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public StudentDTO GetStudent(String email) {
        Student student = studentRepository.findByEmail(email);
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
        // newStudent.setAddress(student.getAddress()).setClassName(classs).setDob(student.getDob()).setEmail(student.getEmail()).setGender(student.getGender()).setImage(student.getImage()).setName(student.getName()).setPhone(student.getPhone()).setStatus(student.getStatus());
        Student saveStudent = modelMapper.map(student, Student.class);
        studentRepository.save(saveStudent);

        // if (newStudent == null) {
        // throw new StudentNotFoundException("Student " + student.getEmail() + " cant
        // be found");
        // }
        // Classes classs = classesRepository.findByName(student.getClassName());
        // newStudent.setAddress(student.getAddress()).setClassName(classs).setDob(student.getDob())
        // .setEmail(student.getEmail()).setGender(student.getGender()).setImage(student.getImage())
        // .setName(student.getName()).setPhone(student.getPhone()).setStatus(student.getStatus());
        // studentRepository.save(newStudent);

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
    public String DeleteStudent(String email) {
        Student deleteStudent = studentRepository.findByEmail(email);

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
            throw new StudentException("Student already exists");
        }

        Student newStudent = modelMapper.map(student, Student.class);
        Classes classes = classesRepository.findByName(student.getClassName());
        List<Classes> classList = new ArrayList<Classes>();
        classList.add(classes);
        if (classes == null) {
            throw new StudentException("Class is not found");
        }
        studentRepository.save(newStudent.setClassName(classList));
        return student;
    }

    public String upgradeClass(String className,String email){
        try {
        Classes classes = classesRepository.findByName(className);
        // Classes classes = new Classes(0,className,)
        Student student = studentRepository.findByEmail(email);
        List<Classes> classList = new ArrayList<Classes>();
        classList.add(classes);
        student.setClassName(classList);
        studentRepository.save(student);
        return "upgrade successfullly";
        } catch (Exception e) {
            throw new StudentException("upgrade error, " + e.getMessage());
        }
    }

    public class StudentException extends RuntimeException {
        public StudentException(String message) {
            super(message);
        }
    }

    // @Override
    // public Student GiveAccessAccount(String email,Student student) {
    // Optional<User> user = userRepository.findByEmail(email);
    // return student.setUser(user.get());
    // // if (student.getUser() != null) {
    // // throw new StudentException("Student has already been added the user " +
    // student.getUser().getEmail());
    // // }
    // // if (user.isPresent()&&student!=null) {
    // // student.setUser(user.get());
    // // studentRepository.save(student);
    // // return student;
    // // } else {
    // // throw new StudentException("Student " +email+ " does not exist");
    // // }
    // }
  
//     @Override
//     public Long generateAccount() {
//         Long totalRowInStudent = studentRepository.count();
//         List <Student> list = studentRepository.findByUser(null);
//         // list.stream().forEach(student -> studentRepository.save(studentService.GiveAccessAccount(student.getEmail(),student)));
//         list.stream().forEach(student -> {
//             char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();
//             String randomStr = RandomStringUtils.random( 6, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
// // System.out.println( randomStr );
//             UserDto userDto = userService.signup(new UserDto(student.getEmail(), randomStr, new RoleDto("STUDENT")));
//             Optional<User> user = userRepository.findByEmail(userDto.getEmail());
//             if (!user.isPresent()){
//                 throw new StudentException("User not found: " + userDto.getEmail());
//             }
//             studentRepository.save(student.setUser(user.get()));
//         });
//         return totalRowInStudent;
//         //Student newStudent = modelMapper.map(student, Student.class);
//         //studentRepository.save(newStudent);
//         //return student;
//     }



    // huyen
    public List<Student> findByClassId(Long classId) {
        return studentRepository.findByClassId(classId);
    }

    @Override
    public String ConfirmStudent(StudentDTO studentDTO) {
        Student student = studentRepository.findByEmail(studentDTO.getEmail());
        Student confirmStudent = modelMapper.map(studentDTO, Student.class);
        student.setImage(null);
        confirmStudent.setClassName(student.getClassName()).setUser(null).setImage(null);
        if(student.equals(confirmStudent)){
            char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();
            String randomStr = RandomStringUtils.random( 6, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
// System.out.println( randomStr );
            userService.signup(new UserDto(student.getEmail(), randomStr, new RoleDto("STUDENT")));
            User user = userRepository.findByEmail(student.getEmail()).get();
            student.setUser(user);
            student.setStatus("active");
            studentRepository.save(student);
            return "Confirmed Successfully";
        }else{
            throw new StudentException("Wrong information, if the system wrong please mail to longpntts2109002@fpt.edu.vn");
        }
    }

}
