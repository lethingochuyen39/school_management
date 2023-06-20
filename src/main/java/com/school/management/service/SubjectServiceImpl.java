package com.school.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.AddSubject;
import com.school.management.dto.SubjectDto;
import com.school.management.dto.TeacherDto;
import com.school.management.model.Subject;
import com.school.management.model.Teacher;
import com.school.management.repository.SubjectRepository;
import com.school.management.repository.TeacherRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public SubjectDto createSubject(AddSubject subject) {
        Optional<Teacher> teacher = teacherRepository.findById(subject.getId());
        if (subject.getName() == null || subject.getId() == null) {
            throw new IllegalArgumentException("Subject and Teacher are required.");
        } else if (subjectRepository.existsByName(subject.getName())
                && teacher.isPresent()) {
            throw new IllegalArgumentException("Subject and Teacher are already exists.");
        } else {
            Subject subject2 = new Subject(null, subject.getName(), teacher.get());
            SubjectDto sDto = modelMapper.map(subject2, SubjectDto.class);
            subjectRepository.save(subject2);
            return sDto;
        }
    }

    @Override
    public Subject updateSubject(Long id, SubjectDto subject) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        Teacher teacher = modelMapper.map(subject.getTeacher(), Teacher.class);
        if (optionalSubject.isEmpty()) {
            throw new SubjectNotFoundException("Subject not found with id: " + id);
        }
        Subject existingSubject = optionalSubject.get();

        if (subject.getName() == null || subject.getTeacher() == null) {
            throw new IllegalArgumentException("Subject and Teacher are required.");
        }

        existingSubject.setName(subject.getName());
        existingSubject.setTeacher(teacher);

        return subjectRepository.save(existingSubject);
    }

    @Override
    public boolean deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new SubjectNotFoundException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
        return true;
    }

    @Override
    public List<SubjectDto> getAllSubject() {
        List<Subject> list = subjectRepository.findAll();

        List<SubjectDto> listDto = new ArrayList<>();
        for (var i : list) {
            SubjectDto dto = new SubjectDto();
            TeacherDto teacher = new TeacherDto().setName(i.getTeacher().getName())
                    .setAddress(i.getTeacher().getAddress()).setDob(i.getTeacher().getDob())
                    .setEmail(i.getTeacher().getEmail()).setGender(i.getTeacher().getGender())
                    .setPhone(i.getTeacher().getPhone()).setStatus(i.getTeacher().getStatus())
                    .setImage(i.getTeacher().getImage());
            // TeacherDto teacher = modelMapper.map(i.getTeacher(), TeacherDto.class);
            // Teacher test = i.getTeacher();
            dto.setName(i.getName()).setTeacher(teacher);
            listDto.add(dto);
        }
        return listDto;
    }

    // @Override
    // public SubjectDto getSubjectById(Long id) {
    // return subjectRepository.findById(id)
    // .orElseThrow(() -> new SubjectNotFoundException("Class not found with id: " +
    // id));
    // }

    @Override
    public List<SubjectDto> getSubjectByName(String name) {
        List<Subject> list = subjectRepository.findByNameContainingIgnoreCase(name);
        List<SubjectDto> listDto = new ArrayList<>();
        for (var i : list) {
            SubjectDto dto = new SubjectDto();
            TeacherDto teacher = modelMapper.map(i.getTeacher(), TeacherDto.class);
            dto.setName(i.getName()).setTeacher(teacher);
            listDto.add(dto);
        }
        return listDto;
    }

    public class SubjectNotFoundException extends RuntimeException {
        public SubjectNotFoundException(String message) {
            super(message);
        }
    }
}
