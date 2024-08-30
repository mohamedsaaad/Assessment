package com.mod5r.assesment.controllers;

import com.mod5r.assesment.dto.input.SignInDto;
import com.mod5r.assesment.dto.input.SignUpDto;
import com.mod5r.assesment.dto.input.UserVerificationDto;
import com.mod5r.assesment.dto.output.ApiResponse;
import com.mod5r.assesment.dto.output.LoginResponseDto;
import com.mod5r.assesment.dto.output.UserOutputDto;
import com.mod5r.assesment.entities.User;
import com.mod5r.assesment.services.AuthenticationService;
import com.mod5r.assesment.services.JwtService;
import com.mod5r.assesment.services.LocalizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    protected final static String LANGUAGE_HEADER = "Content-Language";
    protected final static String LANGUAGE_HEADER_VALUE = "en";

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final LocalizationService localizationService;


    public AuthenticationController(JwtService jwtService
            , AuthenticationService authenticationService
            , LocalizationService localizationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.localizationService = localizationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserOutputDto>> register(@RequestBody SignUpDto registerUserDto
    , @RequestHeader(name = LANGUAGE_HEADER, defaultValue = LANGUAGE_HEADER_VALUE) String language) throws Exception {
        User registeredUser = authenticationService.signup(registerUserDto,language);
        UserOutputDto dto=UserOutputDto
                .builder()
                .userName(registeredUser.getUsername())
                .email(registeredUser.getEmail())
                .build();
        ApiResponse<UserOutputDto> response = new ApiResponse<>(0, "", dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> authenticate(@RequestBody SignInDto loginUserDto
            , @RequestHeader(name = LANGUAGE_HEADER, defaultValue = LANGUAGE_HEADER_VALUE) String language) throws AuthenticationException, Exception{

        User authenticatedUser = authenticationService.authenticate(loginUserDto,language);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponseDto loginResponse =LoginResponseDto
                .builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        ApiResponse<LoginResponseDto> response = new ApiResponse<>(0, "", loginResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestBody UserVerificationDto verifyUserDto
            , @RequestHeader(name = LANGUAGE_HEADER, defaultValue = LANGUAGE_HEADER_VALUE) String language) throws Exception{

        authenticationService.verifyUser(verifyUserDto,language);
        ApiResponse<String> response = new ApiResponse<>(0, ""
                , localizationService.getMessage("account.verify.success", language));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend")
    public ResponseEntity<ApiResponse<String>> resendVerificationCode(@RequestParam String email
            , @RequestHeader(name = LANGUAGE_HEADER, defaultValue = LANGUAGE_HEADER_VALUE) String language) throws Exception {

        authenticationService.resendVerificationCode(email,language);
        ApiResponse<String> response = new ApiResponse<>(0, ""
                , localizationService.getMessage("verification.code.sent", language));

        return ResponseEntity.ok(response);
    }
}
