package com.school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.dto.LoginRequest;
import com.school.management.dto.TokenRefreshDto;
import com.school.management.dto.UserDto;
import com.school.management.exception.TokenRefreshException;
import com.school.management.model.RefreshToken;
import com.school.management.service.RefreshTokenService;
import com.school.management.service.UserService;
import com.school.management.utils.JwtUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));

    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequest login) {

        throw new IllegalStateException(
                "This method shouldn't be called. It's implemented by Spring Security filters.");
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshDto request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    return ResponseEntity.ok(jwtUtils.generateTokenFromEmail(user.getEmail()));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

}
