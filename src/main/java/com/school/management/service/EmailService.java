package com.school.management.service;

import java.io.UnsupportedEncodingException;

import com.school.management.dto.TokenResetPasswordDTO;

import jakarta.mail.MessagingException;

public interface EmailService {
	TokenResetPasswordDTO sendSimpleMail(String email, String code) throws MessagingException, UnsupportedEncodingException;
}