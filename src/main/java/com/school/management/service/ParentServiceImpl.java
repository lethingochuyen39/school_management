package com.school.management.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.school.management.dto.ParentDTO;
import com.school.management.dto.RoleDto;
import com.school.management.dto.UserDto;
import com.school.management.model.Parent;
import com.school.management.model.User;
import com.school.management.repository.ParentRepository;
import com.school.management.repository.UserRepository;

public class ParentServiceImpl implements ParentService{
    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public ParentDTO GetParent(String email) {
        Parent parent = parentRepository.findByEmail(email);
        if (parent == null){
            throw new ParentException("Parent does not exist");
        }
        ParentDTO parentDTO = modelMapper.map(parent, ParentDTO.class);
        return parentDTO;
    }

    @Override
    public ParentDTO UpdateProfile(ParentDTO parent) {
        Parent updateParent = parentRepository.findByEmail(parent.getEmail());
        // updateParent.setAddress(parent.getAddress()).setDob(null);
        Parent saveParent = modelMapper.map(updateParent, Parent.class);
        parentRepository.save(saveParent);
        return parent;
    }

    @Override
    public List<ParentDTO> GetAllParent() {
        List<ParentDTO> parents = new ArrayList<>();
        parentRepository.findAll().stream().forEach(parent->parents.add(modelMapper.map(parent, ParentDTO.class)));
        return parents;
    }

    @Override
    public String DeleteParent(String email) {
        Parent deleteParent = parentRepository.findByEmail(email);
        if(deleteParent == null) {
            throw new ParentException("Parent " + email + " cant be found");
        }
        parentRepository.delete(deleteParent);
        return "Delete Successfully";
    }

    @Override
    public ParentDTO AddParent(ParentDTO parent) {
        Parent existParent = parentRepository.findByEmail(parent.getEmail());
        if (existParent != null) {
            throw new ParentException("Parent already exists");
        }
        Parent newParent = modelMapper.map(parent,Parent.class);
        
        parentRepository.save(newParent);
        return parent;
    }

    @Override
    public Long generateAccount() {
        Long totalRowInStudent = parentRepository.count();
        List <Parent> list = parentRepository.findByUser(null);
        // list.stream().forEach(student -> studentRepository.save(studentService.GiveAccessAccount(student.getEmail(),student)));
        list.stream().forEach(student -> {
            char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();
            String randomStr = RandomStringUtils.random( 6, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
// System.out.println( randomStr );
            UserDto userDto = userService.signup(new UserDto(student.getEmail(), randomStr, new RoleDto("STUDENT")));
            Optional<User> user = userRepository.findByEmail(userDto.getEmail());
            if (!user.isPresent()){
                throw new ParentException("User not found: " + userDto.getEmail());
            }
            parentRepository.save(student.setUser(user.get()));
        });
        return totalRowInStudent;
    }

    public class ParentException extends RuntimeException {
        public ParentException(String message) {
            super(message);
        }
    }
}
