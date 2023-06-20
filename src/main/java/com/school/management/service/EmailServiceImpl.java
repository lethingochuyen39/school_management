package com.school.management.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

// import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.school.management.model.User;
import com.school.management.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JavaMailSender emailSender;
	@Value("${spring.mail.username}")
	private String sender;

	// @Autowired
	// private UserService userService;

	@Override
	public String sendSimpleMail(String email, String link) throws MessagingException, UnsupportedEncodingException{
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			Optional<User> user = userRepository.findByEmail(email);
			String content = "<p>Hello,</p>"
            + "<p>You have requested to reset your password.</p>"
            + "<p>Click the link below to change your password:</p>"
            + "<p><a href=\"" + link + "\">Change my password</a></p>"
            + "<br>"
            + "<p>Ignore this email if you do remember your password, "
            + "or you have not made the request.</p>";
			if (user == null)
				return null;
			// Creating a simple mail message
			// SimpleMailMessage mailMessage = new SimpleMailMessage();
			
			// Setting up necessary details
			helper.setFrom(sender);
			helper.setTo(email);
			helper.setText(content,true);
			helper.setSubject("Reset Password");
			// Sending the mail
			emailSender.send(message);
			// TokenResetPasswordDTO token = new TokenResetPasswordDTO();
			// token.setResetPasswordToken(code);
			return "Send successfully";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			return null;
		}
	}
	// long

}