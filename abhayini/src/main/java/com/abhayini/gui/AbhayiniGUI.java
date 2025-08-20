//package com.abhayini.gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.abhayini.dto.LoginRequest;
//import com.abhayini.dto.RegisterRequest;
//import com.abhayini.dto.TwoFactorRequest;
//import com.abhayini.model.UserRole;
//import com.abhayini.service.AuthService;
//
///**
// * Main GUI class for the Abhayini application.
// * Provides a Swing-based interface for authentication and 2FA flows.
// */
//public class AbhayiniGUI extends JFrame {
//    private final AuthService authService; // Service to handle authentication logic
//    private CardLayout cardLayout; // Layout manager for switching between panels
//    private JPanel mainPanel; // Main container for all panels
//
//    // Panels for different screens
//    private JPanel loginPanel, registerPanel, twoFactorPanel, welcomePanel;
//
//    // Login fields
//    private JTextField loginEmailField; // Input field for login email
//    private JPasswordField loginPasswordField; // Input field for login password
//
//    // Register fields
//    private JTextField registerUsernameField, registerEmailField; // Input fields for registration
//    private JPasswordField registerPasswordField; // Input field for registration password
//    private JComboBox<UserRole> roleComboBox; // Dropdown for selecting user role
//
//    // 2FA fields
//    private JTextField twoFactorEmailField, twoFactorCodeField; // Input fields for 2FA email and code
//
//    // Welcome label
//    private JLabel welcomeLabel; // Label to display welcome message
//
//    // Constructor to initialize the GUI
//    public AbhayiniGUI(AuthService authService) {
//        System.out.println("[DEBUG] Initializing AbhayiniGUI");
//        this.authService = authService; // Inject the authentication service
//        initializeUI(); // Set up the UI components
//    }
//
//    // Method to initialize the UI components
//    private void initializeUI() {
//        setTitle("Abhayini - Safety App"); // Set the window title
//        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close the application on exit
//        setSize(600, 400); // Set the window size
//        setLocationRelativeTo(null); // Center the window on the screen
//
//        cardLayout = new CardLayout(); // Initialize CardLayout for panel switching
//        mainPanel = new JPanel(cardLayout); // Create the main panel with CardLayout
//
//        // Create and add individual panels
//        createLoginPanel();
//        createRegisterPanel();
//        createTwoFactorPanel();
//        createWelcomePanel();
//
//        // Add panels to the main panel
//        mainPanel.add(loginPanel, "LOGIN");
//        mainPanel.add(registerPanel, "REGISTER");
//        mainPanel.add(twoFactorPanel, "TWOFACTOR");
//        mainPanel.add(welcomePanel, "WELCOME");
//
//        add(mainPanel); // Add the main panel to the frame
//        cardLayout.show(mainPanel, "LOGIN"); // Show the login panel by default
//
//        setVisible(true); // Make the frame visible
//        System.out.println("[DEBUG] Main frame visible");
//    }
//
//    // Method to create the login panel
//    private void createLoginPanel() {
//        loginPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for flexible layout
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5); // Add padding between components
//
//        // Add title label
//        JLabel title = new JLabel("Login to Abhayini");
//        title.setFont(new Font("Arial", Font.BOLD, 18));
//        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 0;
//        loginPanel.add(title, gbc);
//
//        // Add email label and input field
//        gbc.gridwidth = 1;
//        gbc.anchor = GridBagConstraints.EAST;
//        loginPanel.add(new JLabel("Email:"), gbc);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        loginEmailField = new JTextField(20);
//        loginPanel.add(loginEmailField, gbc);
//
//        // Add password label and input field
//        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
//        loginPanel.add(new JLabel("Password:"), gbc);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        loginPasswordField = new JPasswordField(20);
//        loginPanel.add(loginPasswordField, gbc);
//
//        // Add login button
//        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
//        JButton loginBtn = new JButton("Login");
//        loginPanel.add(loginBtn, gbc);
//
//        // Add link to register panel
//        gbc.gridy = 4;
//        JButton toRegister = new JButton("Need an account? Register");
//        toRegister.setBorderPainted(false);
//        toRegister.setContentAreaFilled(false);
//        toRegister.setForeground(Color.BLUE);
//        loginPanel.add(toRegister, gbc);
//
//        // Add message label for error/success messages
//        JLabel msgLbl = new JLabel();
//        msgLbl.setForeground(Color.RED);
//        gbc.gridy = 5;
//        loginPanel.add(msgLbl, gbc);
//
//        // Add action listener for login button
//        loginBtn.addActionListener(e -> {
//            try {
//                System.out.println("[DEBUG] Login button clicked");
//                LoginRequest req = new LoginRequest();
//                req.setEmail(loginEmailField.getText());
//                req.setPassword(new String(loginPasswordField.getPassword()));
//                String resp = authService.login(req); // Call login method
//                msgLbl.setText(resp); // Display response message
//                twoFactorEmailField.setText(req.getEmail()); // Set email for 2FA
//                cardLayout.show(mainPanel, "TWOFACTOR"); // Switch to 2FA panel
//            } catch (Exception ex) {
//                msgLbl.setText(ex.getMessage()); // Display error message
//            }
//        });
//
//        // Add action listener for register link
//        toRegister.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER"));
//    }
//
//    // Method to create the registration panel
//    private void createRegisterPanel() {
//        registerPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        // Add title label
//        JLabel title = new JLabel("Register for Abhayini");
//        title.setFont(new Font("Arial", Font.BOLD, 18));
//        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 0;
//        registerPanel.add(title, gbc);
//
//        // Add username label and input field
//        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
//        registerPanel.add(new JLabel("Username:"), gbc);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        registerUsernameField = new JTextField(20);
//        registerPanel.add(registerUsernameField, gbc);
//
//        // Add email label and input field
//        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
//        registerPanel.add(new JLabel("Email:"), gbc);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        registerEmailField = new JTextField(20);
//        registerPanel.add(registerEmailField, gbc);
//
//        // Add password label and input field
//        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
//        registerPanel.add(new JLabel("Password:"), gbc);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        registerPasswordField = new JPasswordField(20);
//        registerPanel.add(registerPasswordField, gbc);
//
//        // Add role label and dropdown
//        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
//        registerPanel.add(new JLabel("Role:"), gbc);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        roleComboBox = new JComboBox<>(UserRole.values());
//        registerPanel.add(roleComboBox, gbc);
//
//        // Add register button
//        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
//        JButton registerBtn = new JButton("Register");
//        registerPanel.add(registerBtn, gbc);
//
//        // Add link to login panel
//        gbc.gridy = 6;
//        JButton toLogin = new JButton("Already have an account? Login");
//        toLogin.setBorderPainted(false);
//        toLogin.setContentAreaFilled(false);
//        toLogin.setForeground(Color.BLUE);
//        registerPanel.add(toLogin, gbc);
//
//        // Add message label for error/success messages
//        JLabel msgLbl = new JLabel();
//        msgLbl.setForeground(Color.RED);
//        gbc.gridy = 7;
//        registerPanel.add(msgLbl, gbc);
//
//        // Add action listener for register button
//        registerBtn.addActionListener(e -> {
//            try {
//                System.out.println("[DEBUG] Register button clicked");
//                RegisterRequest req = new RegisterRequest();
//                req.setUsername(registerUsernameField.getText());
//                req.setEmail(registerEmailField.getText());
//                req.setPassword(new String(registerPasswordField.getPassword()));
//                req.setRole((UserRole) roleComboBox.getSelectedItem());
//                String resp = authService.register(req); // Call register method
//                JOptionPane.showMessageDialog(this, resp); // Show success message
//                cardLayout.show(mainPanel, "LOGIN"); // Switch to login panel
//            } catch (Exception ex) {
//                msgLbl.setText(ex.getMessage()); // Display error message
//            }
//        });
//
//        // Add action listener for login link
//        toLogin.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
//    }
//
//    // Method to create the two-factor authentication panel
//    private void createTwoFactorPanel() {
//        twoFactorPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        // Add title label
//        JLabel title = new JLabel("Two-Factor Authentication");
//        title.setFont(new Font("Arial", Font.BOLD, 18));
//        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 0;
//        twoFactorPanel.add(title, gbc);
//
//        // Hidden email field for tracking
//        twoFactorEmailField = new JTextField(20);
//        twoFactorEmailField.setVisible(false);
//
//        // Add code label and input field
//        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
//        twoFactorPanel.add(new JLabel("Code:"), gbc);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        twoFactorCodeField = new JTextField(10);
//        twoFactorPanel.add(twoFactorCodeField, gbc);
//
//        // Add verify button
//        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
//        JButton verifyBtn = new JButton("Verify");
//        twoFactorPanel.add(verifyBtn, gbc);
//
//        // Add back to login button
//        gbc.gridy = 4;
//        JButton backBtn = new JButton("Back to Login");
//        twoFactorPanel.add(backBtn, gbc);
//
//        // Add message label for error/success messages
//        JLabel msgLbl = new JLabel();
//        msgLbl.setForeground(Color.RED);
//        gbc.gridy = 5;
//        twoFactorPanel.add(msgLbl, gbc);
//
//        // Add action listener for verify button
//        verifyBtn.addActionListener(e -> {
//            try {
//                System.out.println("[DEBUG] Verify 2FA clicked");
//                String email = twoFactorEmailField.getText();
//                String code = twoFactorCodeField.getText();
//                String resp = authService.verifyTwoFactor(email, code); // Call 2FA verification
//                welcomeLabel.setText("Welcome! " + resp); // Update welcome message
//                cardLayout.show(mainPanel, "WELCOME"); // Switch to welcome panel
//            } catch (Exception ex) {
//                msgLbl.setText(ex.getMessage()); // Display error message
//            }
//        });
//
//        // Add action listener for back button
//        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
//    }
//
//    // Method to create the welcome panel
//    private void createWelcomePanel() {
//        welcomePanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        // Add welcome label
//        welcomeLabel = new JLabel("Welcome to Abhayini!");
//        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
//        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.CENTER;
//        welcomePanel.add(welcomeLabel, gbc);
//
//        // Add description label
//        JLabel desc = new JLabel("<html><center>Abhayini is your safety companion.<br>Use this app to stay connected with trusted contacts.</center></html>");
//        gbc.gridy = 1;
//        welcomePanel.add(desc, gbc);
//
//        // Add logout button
//        gbc.gridy = 2;
//        JButton logoutBtn = new JButton("Logout");
//        welcomePanel.add(logoutBtn, gbc);
//
//        // Add action listener for logout button
//        logoutBtn.addActionListener(e -> {
//            System.out.println("[DEBUG] Logout clicked");
//            cardLayout.show(mainPanel, "LOGIN"); // Switch to login panel
//        });
//    }
//
//    // Main method to launch the application
//    public static void main(String[] args) {
//        System.out.println("[DEBUG] Launching AbhayiniGUI application");
//        SwingUtilities.invokeLater(() -> new AbhayiniGUI(new MockAuthService())); // Use mock service for testing
//    }
//
//    // Mock implementation of AuthService for UI testing
//    private static class MockAuthService implements AuthService {
//        private Map<String, String> users = new HashMap<>(); // Store registered users
//        private Map<String, String> codes = new HashMap<>(); // Store 2FA codes
//
//        @Override
//        public String register(RegisterRequest r) {
//            if (users.containsKey(r.getEmail())) throw new RuntimeException("User exists");
//            users.put(r.getEmail(), r.getPassword()); // Save user credentials
//            return "Registered.";
//        }
//
//        @Override
//        public String login(LoginRequest r) {
//            if (!users.containsKey(r.getEmail())) throw new RuntimeException("Not found");
//            if (!users.get(r.getEmail()).equals(r.getPassword())) throw new RuntimeException("Invalid creds");
//            String code = String.format("%06d", (int) (Math.random() * 1000000)); // Generate random 2FA code
//            codes.put(r.getEmail(), code); // Save 2FA code
//            JOptionPane.showMessageDialog(null, "2FA code: " + code); // Display 2FA code
//            return "Enter code.";
//        }
//
//        @Override
//        public String verifyTwoFactor(String email, String code) {
//            if (!code.equals(codes.get(email))) throw new RuntimeException("Wrong code");
//            codes.remove(email); // Remove used 2FA code
//            return email; // Return email on successful verification
//        }
//    }
//}