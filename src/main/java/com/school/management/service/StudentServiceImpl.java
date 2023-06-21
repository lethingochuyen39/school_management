package com.school.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.StudentDTO;
import com.school.management.model.Classes;
import com.school.management.model.Student;
import com.school.management.model.User;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.UserRepository;
@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StudentDTO GetStudent(String email) {
        Student student = studentRepository.findByEmail(email);
        StudentDTO model = modelMapper.map(student, StudentDTO.class);
        return model;
    }

    @Override
    public StudentDTO UpdateProfile(StudentDTO student) {
        Student newStudent = studentRepository.findByEmail(student.getEmail());
        if(newStudent == null) {
            throw new StudentException("Student "+student.getEmail()+" cant be found");
        }
        Classes classs = classesRepository.findByName(student.getClassName());
        newStudent.setAddress(student.getAddress()).setClassName(classs).setDob(student.getDob()).setEmail(student.getEmail()).setGender(student.getGender()).setImage(student.getImage()).setName(student.getName()).setPhone(student.getPhone()).setStatus(student.getStatus());
        studentRepository.save(newStudent);
        return student;
    }

    @Override
    public List<StudentDTO> GetAllStudent() {
        List<StudentDTO> students = new ArrayList<>();
        studentRepository.findAll().stream().forEach(student->students.add(modelMapper.map(student, StudentDTO.class)));
        return students;
    }

    @Override
    public String DeleteStudent(String email) {
        Student deleteStudent = studentRepository.findByEmail(email);
        if(deleteStudent == null) {
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
        Student newStudent = modelMapper.map(student,Student.class);
        studentRepository.save(newStudent);
        return student;
    }
    public class StudentException extends RuntimeException {
		public StudentException(String message) {
			super(message);
		}
	}
    @Override
    public Student GiveAccessAccount(String email,Student student) {
        Optional<User> user = userRepository.findByEmail(email);
        return student.setUser(user.get());
        // if (student.getUser() != null) {
        //     throw new StudentException("Student has already been added the user " + student.getUser().getEmail());
        // }
        // if (user.isPresent()&&student!=null) {
        //     student.setUser(user.get());
        //     studentRepository.save(student);
        //     return student;
        // } else {
        //     throw new StudentException("Student " +email+ " does not exist");
        // }
    }
}
