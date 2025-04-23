// Part 1 (for config pkg): security configuration
// This class is used to configure the password encoder and security filter chain for the application.


// src/main/java/com/abhayini/config/SecurityConfig.java

package com.abhayini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    // Configures the password encoder for the application.
    // BCryptPasswordEncoder is used to encode passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configures the security filter chain for the application.
    // This method sets up the security rules for HTTP requests.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disables CSRF protection.
                .authorizeHttpRequests(auth -> auth
                        // Allows unauthenticated access to the registration, login, and 2FA verification endpoints.
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/verify2fa").permitAll()
                        // Requires authentication for all other requests.
                        .anyRequest().authenticated() // for authentication
                )
                // Configures HTTP Basic authentication.
                .httpBasic(withDefaults());
        return http.build(); // Builds and returns the SecurityFilterChain.
    }
}