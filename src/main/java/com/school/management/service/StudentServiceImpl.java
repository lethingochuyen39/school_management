package com.school.management.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.StudentDTO;
import com.school.management.model.Classes;
import com.school.management.model.Student;
import com.school.management.repository.ClassesRepository;
import com.school.management.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassesRepository classesRepository;

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
        if (newStudent == null) {
            throw new StudentNotFoundException("Student " + student.getEmail() + " cant be found");
        }
        Classes classs = classesRepository.findByName(student.getClassName());
        newStudent.setAddress(student.getAddress()).setClassName(classs).setDob(student.getDob())
                .setEmail(student.getEmail()).setGender(student.getGender()).setImage(student.getImage())
                .setName(student.getName()).setPhone(student.getPhone()).setStatus(student.getStatus());
        studentRepository.save(newStudent);
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
            throw new StudentNotFoundException("Student " + email + " cant be found");
        }
        studentRepository.delete(deleteStudent);
        return "Delete Successfully";
    }

    public class StudentNotFoundException extends RuntimeException {
        public StudentNotFoundException(String message) {
            super(message);
        }
    }

    @Override
    public StudentDTO AddStudent(StudentDTO student) {
        Student existStudent = studentRepository.findByEmail(student.getEmail());
        if (existStudent != null) {
            throw new StudentFoundException("Student already exists");
        }
        Student newStudent = modelMapper.map(student, Student.class);
        studentRepository.save(newStudent);
        return student;
    }

    public class StudentFoundException extends RuntimeException {
        public StudentFoundException(String message) {
            super(message);
        }
    }

    // huyen
    public List<Student> findByClassId(Long classId) {
        return studentRepository.findByClassId(classId);
    }
}
