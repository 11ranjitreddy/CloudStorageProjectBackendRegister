package com.Cloud.Storage.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOtpEmail(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("CloudVault - Email Verification OTP");
        message.setText(
                "Your OTP for email verification is: " + otp +
                        "\n\nThis OTP is valid for one time use only."
        );

        mailSender.send(message);
    }
}
