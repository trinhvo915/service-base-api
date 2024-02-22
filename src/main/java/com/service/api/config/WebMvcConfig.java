package com.service.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Value("${cors.url}")
    private String corsUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsUrl)
                .allowedMethods("HEAD", "OPTIONS","GET", "POST", "PUT", "DELETE", "PATCH")
                .maxAge(MAX_AGE_SECS);
    }
}
