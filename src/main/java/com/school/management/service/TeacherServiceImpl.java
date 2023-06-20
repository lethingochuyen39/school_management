package com.school.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.TeacherDto;
import com.school.management.model.Teacher;
import com.school.management.model.User;
import com.school.management.repository.TeacherRepository;
import com.school.management.repository.UserRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
public Teacher createTeacher(TeacherDto teacherDto) {
    if (teacherDto.getName() == null || teacherDto.getGender() == null ||
            teacherDto.getDob() == null
            || teacherDto.getEmail() == null || teacherDto.getAddress() == null ||
            teacherDto.getPhone() == null
            || teacherDto.getStatus() == null || teacherDto.getImage() == null) {
        throw new IllegalArgumentException(
                "Name, Gender, Dob, Email, Address, Phone, Status, and Image are required.");
    } else {
        Optional<User> user = userRepository.findByEmail(teacherDto.getEmail());
        
        Teacher teacher = new Teacher();
        teacher.setName(teacherDto.getName());
        teacher.setGender(teacherDto.getGender());
        teacher.setDob(teacherDto.getDob());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setAddress(teacherDto.getAddress());
        teacher.setPhone(teacherDto.getPhone());
        teacher.setStatus(teacherDto.getStatus());
        teacher.setImage(teacherDto.getImage());
        teacher.setUser(user.orElseThrow());

        return teacherRepository.save(teacher);
    }
}

@Override
public Teacher updateTeacher(Long id, TeacherDto teacherDto) {
    if (!teacherRepository.existsById(id)) {
        throw new TeacherNotFoundException("Teacher not found with id: " + id);
    }
    if (teacherDto.getName() == null || teacherDto.getGender() == null || teacherDto.getDob() == null
            || teacherDto.getEmail() == null || teacherDto.getAddress() == null || teacherDto.getPhone() == null
            || teacherDto.getStatus() == null || teacherDto.getImage() == null) {
        throw new IllegalArgumentException("Name, Gender, Dob, Email, Address, Phone, Status, and Image are required.");
    }
    Optional<User> user = userRepository.findById(id);

    Teacher teacher = teacherRepository.findById(id)
            .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
    teacher.setName(teacherDto.getName());
    teacher.setGender(teacherDto.getGender());
    teacher.setDob(teacherDto.getDob());
    teacher.setEmail(teacherDto.getEmail());
    teacher.setAddress(teacherDto.getAddress());
    teacher.setPhone(teacherDto.getPhone());
    teacher.setStatus(teacherDto.getStatus());
    teacher.setImage(teacherDto.getImage());
    teacher.setUser(user.orElseThrow());

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
    public List<TeacherDto> getAllTeacher() {
        List<Teacher> list = teacherRepository.findAll();
        List<TeacherDto> listDto = new ArrayList<>();
        for (var i : list) {
            TeacherDto dto = new TeacherDto();
            dto.setName(i.getName()).setAddress(i.getAddress()).setDob(i.getDob()).setGender(i.getGender())
                    .setImage(i.getImage()).setStatus(i.getStatus()).setEmail(i.getEmail());
            listDto.add(dto);
        }
        return listDto;
    }

    // @Override
    // public TeacherDto getTeacherByEmail(String email) {
    //     Teacher teacher2 = teacherRepository.findByEmail(email);
    //     TeacherDto dto = new TeacherDto(teacher2.getName(), teacher2.getGender(), teacher2.getDob(),
    //             teacher2.getEmail(), teacher2.getAddress(), teacher2.getPhone(), teacher2.getStatus(),
    //             teacher2.getImage());
    //     return dto;
    // }

    @Override
    public List<TeacherDto> getTeacherByName(String name) {
        List<Teacher> list = teacherRepository.findByNameContainingIgnoreCase(name);
        List<TeacherDto> listDto = new ArrayList<>();
        for (var i : list) {
            TeacherDto dto = new TeacherDto();
            dto.setName(i.getName()).setAddress(i.getAddress()).setDob(i.getDob()).setGender(i.getGender())
                    .setImage(i.getImage()).setStatus(i.getStatus()).setEmail(i.getEmail());
            listDto.add(dto);
        }
        return listDto;
    }

    public class TeacherNotFoundException extends RuntimeException {
        public TeacherNotFoundException(String message) {
            super(message);
        }
    }

}
