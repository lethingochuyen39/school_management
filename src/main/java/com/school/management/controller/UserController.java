package com.school.management.controller;

import java.io.UnsupportedEncodingException;

import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.dto.ForgotPassRequest;
import com.school.management.dto.LoginRequest;
import com.school.management.dto.ResetPassRequest;
import com.school.management.dto.TokenRefreshDto;
import com.school.management.dto.UserDto;
import com.school.management.exception.TokenRefreshException;
import com.school.management.model.RefreshToken;
import com.school.management.service.EmailService;
import com.school.management.service.RefreshTokenService;
import com.school.management.service.UserService;
import com.school.management.utils.JwtUtils;
import com.school.management.utils.Utility;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
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
	@Autowired
	private EmailService emailService;

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


	@PostMapping("/forgot_password")
	public ResponseEntity<?> forgotpassword(HttpServletRequest request, @RequestBody ForgotPassRequest email) {
        String token = RandomString.make(30);
        // String email = request.getParameter("email");
            try {
                if(userService.checkUserExistByEmail(email.getEmail())){
                    String resetPasswordLink =  /*Utility.getSiteURL(request)*/"http://localhost:3000" +"/reset_password?token="+ token;
                    // userService.updateResetPasswordToken(token,email);
                    return ResponseEntity.ok(emailService.sendSimpleMail(email.getEmail(), resetPasswordLink,token));
                }
                else{
                    return ResponseEntity.badRequest().body("Cannot find user");
                }
            } catch (UnsupportedEncodingException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (MessagingException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
	}

    @PostMapping("/reset_password")
	public ResponseEntity<?> resetPassword(@RequestParam("token") String token,@RequestBody ResetPassRequest resetPassRequest) {
        // userService.updatePassword(user, password);
        String password = resetPassRequest.getPassword();
        return ResponseEntity.ok(userService.updateResetPasswordToken(token, password));
	}

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestBody String email){
        return ResponseEntity.ok(userService.deleteAccount(email));
    }
}
