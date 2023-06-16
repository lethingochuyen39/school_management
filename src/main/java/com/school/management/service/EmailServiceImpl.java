package com.school.management.service;

import java.util.Optional;

// import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.school.management.model.User;
import com.school.management.repository.UserRepository;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleMail(String email) {
         try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user==null) return null;
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                = new SimpleMailMessage();
 
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            mailMessage.setText("Press here to get new password");
            mailMessage.setSubject("Reset Password");
 
            // Sending the mail
            emailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
//long

}