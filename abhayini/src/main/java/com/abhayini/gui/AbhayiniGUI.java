package com.abhayini.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.abhayini.dto.LoginRequest;
import com.abhayini.dto.RegisterRequest;
import com.abhayini.dto.TwoFactorRequest;
import com.abhayini.model.UserRole;
import com.abhayini.service.AuthService;

/**
 * Main GUI class for the Abhayini application.
 * This class provides a Swing-based user interface for the application's authentication features.
 */
public class AbhayiniGUI extends JFrame {

    private final AuthService authService;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Panels for different screens
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel twoFactorPanel;
    private JPanel welcomePanel;

    // Fields for login
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;

    // Fields for registration
    private JTextField registerUsernameField;
    private JTextField registerEmailField;
    private JPasswordField registerPasswordField;
    private JComboBox<UserRole> roleComboBox;

    // Fields for 2FA
    private JTextField twoFactorEmailField;
    private JTextField twoFactorCodeField;

    // Welcome panel components
    private JLabel welcomeLabel;

    /**
     * Constructor for the GUI
     * @param authService the authentication service to use
     */
    public AbhayiniGUI(AuthService authService) {
        this.authService = authService;
        initializeUI();
    }

    /**
     * Initialize the UI components
     */
    private void initializeUI() {
        setTitle("Abhayini - Safety App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        createLoginPanel();
        createRegisterPanel();
        createTwoFactorPanel();
        createWelcomePanel();

        // Add panels to main panel
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(twoFactorPanel, "TWOFACTOR");
        mainPanel.add(welcomePanel, "WELCOME");

        // Show login panel first
        cardLayout.show(mainPanel, "LOGIN");

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Creates the login panel
     */
    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel titleLabel = new JLabel("Login to Abhayini");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(emailLabel, gbc);

        loginEmailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(loginEmailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(passwordLabel, gbc);

        loginPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(loginPasswordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Register link
        JButton registerLink = new JButton("Need an account? Register");
        registerLink.setBorderPainted(false);
        registerLink.setContentAreaFilled(false);
        registerLink.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(registerLink, gbc);

        // Message label
        JLabel messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(messageLabel, gbc);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoginRequest request = new LoginRequest();
                    request.setEmail(loginEmailField.getText());
                    request.setPassword(new String(loginPasswordField.getPassword()));

                    String response = authService.login(request);
                    messageLabel.setText(response);

                    // Show 2FA panel
                    twoFactorEmailField.setText(loginEmailField.getText());
                    cardLayout.show(mainPanel, "TWOFACTOR");
                } catch (Exception ex) {
                    messageLabel.setText(ex.getMessage());
                }
            }
        });

        registerLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "REGISTER");
            }
        });
    }

    /**
     * Creates the registration panel
     */
    private void createRegisterPanel() {
        registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel titleLabel = new JLabel("Register for Abhayini");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(titleLabel, gbc);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        registerPanel.add(usernameLabel, gbc);

        registerUsernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        registerPanel.add(registerUsernameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        registerPanel.add(emailLabel, gbc);

        registerEmailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        registerPanel.add(registerEmailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        registerPanel.add(passwordLabel, gbc);

        registerPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        registerPanel.add(registerPasswordField, gbc);

        // Role
        JLabel roleLabel = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        registerPanel.add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(UserRole.values());
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        registerPanel.add(roleComboBox, gbc);

        // Register button
        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(registerButton, gbc);

        // Login link
        JButton loginLink = new JButton("Already have an account? Login");
        loginLink.setBorderPainted(false);
        loginLink.setContentAreaFilled(false);
        loginLink.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(loginLink, gbc);

        // Message label
        JLabel messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(messageLabel, gbc);

        // Action listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    RegisterRequest request = new RegisterRequest();
                    request.setUsername(registerUsernameField.getText());
                    request.setEmail(registerEmailField.getText());
                    request.setPassword(new String(registerPasswordField.getPassword()));
                    request.setRole((UserRole) roleComboBox.getSelectedItem());

                    String response = authService.register(request);
                    JOptionPane.showMessageDialog(registerPanel, response);
                    cardLayout.show(mainPanel, "LOGIN");
                } catch (Exception ex) {
                    messageLabel.setText(ex.getMessage());
                }
            }
        });

        loginLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "LOGIN");
            }
        });
    }

    /**
     * Creates the two-factor authentication panel
     */
    private void createTwoFactorPanel() {
        twoFactorPanel = new JPanel();
        twoFactorPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel titleLabel = new JLabel("Two-Factor Authentication");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        twoFactorPanel.add(titleLabel, gbc);

        // Instructions
        JLabel instructionsLabel = new JLabel("Please enter the 6-digit code sent to your email");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        twoFactorPanel.add(instructionsLabel, gbc);

        // Email (hidden, for tracking)
        twoFactorEmailField = new JTextField(20);
        twoFactorEmailField.setVisible(false);

        // Verification code
        JLabel codeLabel = new JLabel("Code:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        twoFactorPanel.add(codeLabel, gbc);

        twoFactorCodeField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        twoFactorPanel.add(twoFactorCodeField, gbc);

        // Verify button
        JButton verifyButton = new JButton("Verify");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        twoFactorPanel.add(verifyButton, gbc);

        // Back to login
        JButton backButton = new JButton("Back to Login");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        twoFactorPanel.add(backButton, gbc);

        // Message label
        JLabel messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        twoFactorPanel.add(messageLabel, gbc);

        // Action listeners
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TwoFactorRequest request = new TwoFactorRequest();
                    request.setEmail(twoFactorEmailField.getText());
                    request.setCode(twoFactorCodeField.getText());

                    String response = authService.verifyTwoFactor(request.getEmail(), request.getCode());
                    welcomeLabel.setText("Welcome! " + response);
                    cardLayout.show(mainPanel, "WELCOME");
                } catch (Exception ex) {
                    messageLabel.setText(ex.getMessage());
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "LOGIN");
            }
        });
    }

    /**
     * Creates the welcome panel shown after successful authentication
     */
    private void createWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Welcome message
        welcomeLabel = new JLabel("Welcome to Abhayini!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        welcomePanel.add(welcomeLabel, gbc);

        // App description
        JLabel descriptionLabel = new JLabel("<html><center>Abhayini is your safety companion.<br>Use this application to stay safe and connected with your trusted contacts.</center></html>");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        welcomePanel.add(descriptionLabel, gbc);

        // Features list
        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setBorder(BorderFactory.createTitledBorder("Features"));

        String[] features = {
                "Emergency Alerts",
                "Location Sharing",
                "Trusted Contacts Management",
                "Authority Connection"
        };

        for (String feature : features) {
            JButton featureButton = new JButton(feature);
            featureButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            featureButton.setMaximumSize(new Dimension(200, 30));
            featuresPanel.add(featureButton);
            featuresPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Add action listener to show "Coming soon" message
            featureButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(welcomePanel,
                            feature + " feature coming soon!",
                            "Feature Under Development",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        welcomePanel.add(featuresPanel, gbc);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        welcomePanel.add(logoutButton, gbc);

        // Action listeners
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear fields
                loginEmailField.setText("");
                loginPasswordField.setText("");
                registerUsernameField.setText("");
                registerEmailField.setText("");
                registerPasswordField.setText("");
                twoFactorEmailField.setText("");
                twoFactorCodeField.setText("");

                cardLayout.show(mainPanel, "LOGIN");
            }
        });
    }

    /**
     * Main method to start the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // This would typically be handled by Spring dependency injection
        // For demonstration purposes, we create a mock AuthService
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create a mock AuthService for UI testing
                MockAuthService mockAuthService = new MockAuthService();
                new AbhayiniGUI(mockAuthService);
            }
        });
    }

    /**
     * Mock implementation of AuthService for UI testing
     */
    private static class MockAuthService implements AuthService {
        private Map<String, String> users = new HashMap<>();
        private Map<String, String> tempCodes = new HashMap<>();

        @Override
        public String register(RegisterRequest request) {
            if (users.containsKey(request.getEmail())) {
                throw new RuntimeException("User with this email already exists!");
            }
            users.put(request.getEmail(), request.getPassword());
            return "User registered successfully!";
        }

        @Override
        public String login(LoginRequest request) {
            if (!users.containsKey(request.getEmail())) {
                throw new RuntimeException("User not found!");
            }
            if (!users.get(request.getEmail()).equals(request.getPassword())) {
                throw new RuntimeException("Invalid credentials!");
            }

            // Generate 2FA code
            String code = String.format("%06d", (int)(Math.random() * 1000000));
            tempCodes.put(request.getEmail(), code);

            // In a real app, the code would be sent via email
            JOptionPane.showMessageDialog(null,
                    "Your 2FA code is: " + code + "\n(In a real app, this would be sent to your email)",
                    "2FA Code",
                    JOptionPane.INFORMATION_MESSAGE);

            return "Login successful. Please enter 2FA code.";
        }

        @Override
        public String verifyTwoFactor(String email, String code) {
            if (!tempCodes.containsKey(email) || !tempCodes.get(email).equals(code)) {
                throw new RuntimeException("Invalid 2FA code!");
            }
            tempCodes.remove(email);
            return "Two-factor authentication successful for user: " + email;
        }
    }
}