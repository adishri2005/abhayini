// Part 1 (for config pkg): security configuration
// this class is used to configure the security for the application.

// src/main/java/com/abhayini/config/SecurityConfig.java
package com.abhayini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Indicates that this class contains Spring configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean // Defines a bean for the password encoder
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(); // Returns an instance of BCryptPasswordEncoder
    }

    @Bean // Defines a bean for the security filter chain
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
            // Disable CSRF for REST API
            .csrf(csrf -> csrf.disable())
            
            // Set session management to stateless
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Set permissions on endpoints
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/auth/register", "/auth/login", "/h2-console/**").permitAll()
                // Private endpoints
                .anyRequest().authenticated()
            );
            
        // Allow H2 console frame display
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
            
        return http.build();
    }
}