package com.abhayini;

import com.abhayini.gui.AbhayiniGUI;
import com.abhayini.service.AuthService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class AbhayiniApplication {

    public static void main(String[] args) {
        // Launch the Spring Boot application in a headless environment
        ConfigurableApplicationContext context = new SpringApplicationBuilder(AbhayiniApplication.class)
                .headless(false) // We need a UI, so headless is false
                .run(args);

        // Get the AuthService bean from the Spring context
        AuthService authService = context.getBean(AuthService.class);

        // Launch the Swing GUI
        SwingUtilities.invokeLater(() -> {
            new AbhayiniGUI(authService);
        });
    }
}
