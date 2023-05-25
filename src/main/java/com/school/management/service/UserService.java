package com.school.management.service;

import java.util.List;

import com.school.management.dto.LoginRequest;
import com.school.management.dto.UserDto;
import com.school.management.model.User;

public interface UserService {
    UserDto signup(UserDto user);

    UserDto login(LoginRequest loginDto);

    UserDto findUserByEmail(String email);

    List<User> getAllUser();
}
