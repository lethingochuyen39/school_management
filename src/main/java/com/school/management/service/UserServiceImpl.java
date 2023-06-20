package com.school.management.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.school.management.dto.LoginRequest;
import com.school.management.dto.RoleDto;
import com.school.management.dto.UserDto;
import com.school.management.exception.TokenRefreshException;
import com.school.management.model.Role;
import com.school.management.model.User;
import com.school.management.model.UserRole;
import com.school.management.repository.RoleRepository;
import com.school.management.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto signup(UserDto userDto) {

        Role userRole;
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (!user.isPresent()) {
            String role = userDto.getRole().getRole();
            if (role.equals("ADMIN")) {
                userRole = roleRepository.findByRole(UserRole.ADMIN);
            } else if (role.equals("PARENTS")) {
                userRole = roleRepository.findByRole(UserRole.PARENTS);
            } else if (role.equals("TEACHER")) {
                userRole = roleRepository.findByRole(UserRole.TEACHER);
            } else {
                userRole = roleRepository.findByRole(UserRole.STUDENT);
            }
            // Random random = new Random();
            User newuser = new User().setEmail(userDto.getEmail())
                    .setPassword(passwordEncoder.encode(userDto.getPassword()))
                    .setRole(userRole).setStatus("active").setResetPasswordToken(null);
            userRepository.save(newuser);
            userDto.setPassword("");
            // long

        }

        return userDto;
    }

    @Override
    public UserDto findUserByEmail(String email) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email).get());
        if (user.isPresent()) {
            return modelMapper.map((user.get()), UserDto.class);
        }

        return null;

    }

    @Override
    public UserDto login(LoginRequest loginDto) {
        User user = userRepository.findByEmail(loginDto.getUsername()).get();
        UserDto userDto = new UserDto();
        if (user != null) {
            Boolean checkPass = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
            if (checkPass) {
                RoleDto roles = new RoleDto().setRole(user.getRole().toString());
                // user.getRoles().forEach(role -> {
                // RoleDto roleDto = new RoleDto().setRole(role.getRole().toString());
                // roles.add(roleDto);

                // });
                userDto.setEmail(user.getEmail())
                        .setRole(roles);

                return userDto;
            }

        }

        return userDto;

    }

    @Override
    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public UserDto updateResetPasswordToken(String token, String email) throws TokenRefreshException{
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            user.get().setResetPasswordToken(token);
            userRepository.save(user.get());
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.setPassword("");
            return userDto;
        }else{
            throw new TokenRefreshException(token, "Cannot find token "+token);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) throws RuntimeException{
        Optional<User> user = userRepository.findByResetPasswordToken(token);
        if(user.isPresent()){
            User userDto = modelMapper.map((user.get()), User.class);
            return userDto;
        }
        else{
            return null;
        }
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
    @Override
    public Boolean checkUserExistByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if (user!=null) {
            return true;
        }else{
            return false;
        }

    }
}