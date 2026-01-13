package com.example.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomerConfiguration implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Apply to all endpoints
                .allowedOrigins("http://localhost:8081", "http://localhost:4200")  // Specific origins
                // OR for all origins (NOT recommended for production):
                // .allowedOriginPatterns("*")  // Spring Boot 2.4+
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Content-Type")  // Headers to expose to client
                .allowCredentials(true)  // Allow cookies/auth headers
                .maxAge(3600);  // Cache preflight for 1 hour
    }
}