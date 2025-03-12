// Part 1 (for config pkg): security configuration
// this class is used to configure the password encoder for the application.

package com.abhayini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration                  // indicates that this class contains Spring configuration
public class SecurityConfig {

    @Bean                        // defines a bean for the password encoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();     // returns an instance of BCryptPasswordEncoder
    }
}