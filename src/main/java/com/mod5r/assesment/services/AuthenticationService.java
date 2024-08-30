package com.mod5r.assesment.services;

import com.mod5r.assesment.dto.input.SignInDto;
import com.mod5r.assesment.dto.input.SignUpDto;
import com.mod5r.assesment.dto.input.UserVerificationDto;
import com.mod5r.assesment.entities.User;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    String EMAIL_TEMPLATE_AR="verification_email_ar";
    String EMAIL_TEMPLATE_EN="verification_email_en";
    User signup(SignUpDto input, String language) throws MessagingException;

    User authenticate(SignInDto input, String language);

    void verifyUser(UserVerificationDto input, String language);

    void resendVerificationCode(String email, String language) throws MessagingException;

    void sendVerificationEmail(User user, String language) throws MessagingException;

    String determineTemplateName(String language);

    String generateVerificationCode();
}
