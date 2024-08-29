package com.mod5r.assesment.services;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationService {
    private final MessageSource messageSource;

    public LocalizationService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, String language) {
        Locale locale = new Locale(language);
        return messageSource.getMessage(key, null, locale);
    }
}
