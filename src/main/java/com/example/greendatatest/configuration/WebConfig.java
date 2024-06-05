package com.example.greendatatest.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final StringToFilterConverter stringToFilterConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToFilterConverter);
    }
}

