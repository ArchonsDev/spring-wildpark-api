package com.archons.springwildparkapi.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        ArrayList<String> allowedorigins = new ArrayList<String>();
        allowedorigins.add("http://localhost:3000");

        ArrayList<String> allowedMethods = new ArrayList<String>();
        allowedMethods.add("GET");
        allowedMethods.add("POST");
        allowedMethods.add("PUT");
        allowedMethods.add("DELETE");

        ArrayList<String> allowedHeaders = new ArrayList<String>();
        allowedHeaders.add("*");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedorigins); // You can set specific origins here.
        configuration.setAllowedMethods(allowedMethods); // You can set specific HTTP methods here.
        configuration.setAllowedHeaders(allowedHeaders); // You can set specific headers here.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
