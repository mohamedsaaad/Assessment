package com.mod5r.assesment.config;

import org.springframework.stereotype.Component;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Component
public class LocaleConfig{

    public final static String FILE_PATH= "classpath:messages";
    public final static String ENCODING= "UTF-8";
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(FILE_PATH);
        messageSource.setDefaultEncoding(ENCODING);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
}
