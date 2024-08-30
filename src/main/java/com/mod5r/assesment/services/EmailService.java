package com.mod5r.assesment.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(String to, String subject, String text) throws MessagingException;
}
