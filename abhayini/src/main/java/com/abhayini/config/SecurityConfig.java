// Part 1 (for config pkg): security configuration
// this class is used to configure the password encoder for the application.

// src/main/java/com/abhayini/config/SecurityConfig.java
package com.abhayini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // Indicates that this class contains Spring configuration
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
        http.csrf(csrf -> csrf.disable())                    // disables CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll() // for all

                        //.requestMatchers("/auth/**").permitAll()                  // allows unrestricted access to auth endpoints
                        .anyRequest().authenticated()                            // requires authentication for all other requests
                )
                .httpBasic(withDefaults());                         // enables http basic authentication
        return http.build();                                        // builds the security filter chain
    }
}