package com.mod5r.assesment.services.impl;

import com.mod5r.assesment.dto.input.SignInDto;
import com.mod5r.assesment.dto.input.SignUpDto;
import com.mod5r.assesment.dto.input.UserVerificationDto;
import com.mod5r.assesment.entities.User;
import com.mod5r.assesment.exception.BusinessException;
import com.mod5r.assesment.repositories.UserRepository;
import com.mod5r.assesment.services.AuthenticationService;
import com.mod5r.assesment.services.EmailService;
import com.mod5r.assesment.services.LocalizationService;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final LocalizationService localizationService;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            TemplateEngine templateEngine,
            LocalizationService localizationService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.templateEngine=templateEngine;
        this.localizationService=localizationService;
    }

    @Override
    public User signup(SignUpDto input, String language) throws MessagingException,BusinessException {
        String username=input.getFistName()+" "+input.getSecondName();
        User user =User
                .builder()
                .userName(username)
                .email(input.getEmail())
                .password( passwordEncoder.encode(input.getPassword()))
                .build();

        user.setVerificationCode(generateVerificationCode());
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new BusinessException(localizationService.getMessage("email.already.exist", language));
        }
        userRepository.save(user);
        sendVerificationEmail(user,language);
        return user ;
    }

    @Override
    public User authenticate(SignInDto input, String language) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new BusinessException(localizationService.getMessage("user.not.found", language)));

        if (!user.isEnabled()) {
            throw new BusinessException(localizationService.getMessage("account.not.verified", language));
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return user;
    }

    @Override
    public void verifyUser(UserVerificationDto input, String language) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
                throw new BusinessException(localizationService.getMessage("verification.code.expired", language));
            }
            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationExpiration(null);
                userRepository.save(user);
            } else {
                throw new BusinessException(localizationService.getMessage("invalid.verification.code",language));
            }
        } else {
            throw new BusinessException(localizationService.getMessage("user.not.found",language));
        }
    }

    @Override
    public void resendVerificationCode(String email, String language) throws MessagingException{
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new BusinessException(localizationService.getMessage("account.already.verified", language));
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpiration(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user,language);
            userRepository.save(user);
        } else {
            throw new BusinessException(localizationService.getMessage("user.not.found", language));
        }
    }

    @Override
    public void sendVerificationEmail(User user, String language) throws MessagingException{
        String subject = localizationService.getMessage("account.verification.subject", language);
        String templateName=determineTemplateName(language);
        Context context = new Context();
        context.setVariable("verificationCode", user.getVerificationCode());
        String htmlMessage = templateEngine.process(templateName, context);
        emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
    }

    @Override
    public String determineTemplateName(String language) {
        if (language != null && language.toLowerCase(Locale.ROOT).contains("ar")) {
            return EMAIL_TEMPLATE_AR;
        } else {
            return EMAIL_TEMPLATE_EN;
        }
    }
    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
