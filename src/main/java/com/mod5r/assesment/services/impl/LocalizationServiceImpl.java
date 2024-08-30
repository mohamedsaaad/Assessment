package com.mod5r.assesment.services.impl;
import com.mod5r.assesment.services.LocalizationService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;

    public LocalizationServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override public String getMessage(String key, String language) {
        Locale locale = new Locale(language);
        return messageSource.getMessage(key, null, locale);
    }
}
