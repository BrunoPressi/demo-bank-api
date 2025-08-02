package com.bank_api.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void send(String emailAddressee, String subject, UUID uuid) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("brunopressi2012@gmail.com");
            message.setTo(emailAddressee);
            message.setSubject(subject);
            message.setText("http://localhost:8080/api/v1/email?uuid=".concat(uuid.toString()).concat("&email=".concat(emailAddressee)));
            javaMailSender.send(message);
            log.info("Email Success");
        } catch (MailException e) {
            log.error("Error: {}", e.getMessage());
        }
    }

}
