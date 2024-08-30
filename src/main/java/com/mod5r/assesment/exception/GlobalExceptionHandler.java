package com.mod5r.assesment.exception;

import com.mod5r.assesment.controllers.AuthenticationController;
import com.mod5r.assesment.dto.output.ApiResponse;
import com.mod5r.assesment.services.LocalizationService;
import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private LocalizationService localizationService;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<String>> handleBusinessException(BusinessException ex) {
        ApiResponse<String> response = new ApiResponse<>(1, ex.getMessage(), null);
        logErrorDetails(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex,HttpServletRequest request) {
        String language = request.getHeader("Content-Language");
        String errorMessage = localizationService.getMessage("user.not.found", language);
        ApiResponse<String> response = new ApiResponse<>(1, errorMessage, null);
        logErrorDetails(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex, HttpServletRequest request) {
        String language = request.getHeader("Content-Language");
        String errorMessage = localizationService.getMessage("generic.error.message", language);
        ApiResponse<String> response = new ApiResponse<>(1, errorMessage, null);
        logErrorDetails(ex);
        return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private void logErrorDetails(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace.length > 0) {
            StackTraceElement element = stackTrace[0];
            logger.error("Exception in {}.{}() at line {}: {}",
                    element.getClassName(),
                    element.getMethodName(),
                    element.getLineNumber(),
                    ex.getMessage());
        } else {
            logger.error("Exception: {}", ex.getMessage());
        }
        logger.trace("Stack trace: ", ex);
    }
}
