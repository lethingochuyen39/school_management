package com.school.management.service;

import java.io.UnsupportedEncodingException;


import jakarta.mail.MessagingException;

public interface EmailService {
	String sendSimpleMail(String email, String code) throws MessagingException, UnsupportedEncodingException;
}