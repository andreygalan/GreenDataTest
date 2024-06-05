package com.example.greendatatest.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi restApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .displayName("Test API")
                .pathsToMatch("/**")
                .build();
    }
}
