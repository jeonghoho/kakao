package com.example.demo.config;

import com.example.demo.service.CalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public CalService calService(){
        return new CalService();
    }
}
