package com.example.greendatatest.configuration;

import com.example.greendatatest.repository.Filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringToFilterConverter implements Converter<String, Filter> {

    private final ObjectMapper objectMapper;

    @Override
    public Filter convert(String source) {
        try {
            return objectMapper.readValue(source, Filter.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid filter format", e);
        }
    }
}
