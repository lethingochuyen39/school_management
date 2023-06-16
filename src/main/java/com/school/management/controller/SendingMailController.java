package com.school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.service.EmailService;

@RestController
@RequestMapping("/api")
public class SendingMailController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/forgotpassword")
    public ResponseEntity<String> signup(@RequestBody String mail) {
        return ResponseEntity.ok(emailService.sendSimpleMail(mail));
//long
    }
}
