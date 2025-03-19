//// Part 1 (for service pkg): authentication service
//// this service will handle user registration and login.
//// note the use of a password encoder for security.
//
//package com.abhayini.service;
//
//import com.abhayini.dto.LoginRequest;
//import com.abhayini.dto.RegisterRequest;
//import com.abhayini.model.User;
//import com.abhayini.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service // indicates that this class is a service component in the Spring context
//@RequiredArgsConstructor // lombok annot to generate a constructor with required args
//public class AuthService
//{
//    private final UserRepository userRepository;          // repo to interact with the user entity
//    private final PasswordEncoder passwordEncoder;        // password encoder for hashing passwords
//
//    // method to handle user registration
//    public String register(RegisterRequest request)
//    {
//        // check if a user with the given email already exists
//        if (userRepository.findByEmail(request.getEmail()).isPresent())
//        {
//            throw new RuntimeException("User with this email already exists!");
//        }
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setEmail(request.getEmail());
//        // hash the password before saving
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole(request.getRole());
//
//        userRepository.save(user); // save the new user to the database
//        return "User registered successfully!";
//    }
//
//    // method to handle user login
//    public String login(LoginRequest request)
//    {
//        // find the user by email
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found!"));
//
//        // check if the provided password matches the stored hashed password
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
//        {
//            throw new RuntimeException("Invalid credentials!");
//        }
//        return "Login successful for user: " + user.getUsername();
//    }
//}

package com.abhayini.service;

import com.abhayini.dto.LoginRequest;
import com.abhayini.dto.RegisterRequest;
import com.abhayini.model.User;
import com.abhayini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TwoFactorAuthService twoFactorAuthService;

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists!");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        // Generate and "send" the 2FA code.
        twoFactorAuthService.generateCode(user.getEmail());
        return "Login successful for user: " + user.getUsername() + ". Please verify the 2FA code sent to your email.";
    }

    // This method verifies the 2FA code provided by the user.
    public String verifyTwoFactor(String email, String code) {
        if (twoFactorAuthService.verifyCode(email, code)) {
            return "Two-factor authentication successful for user: " + email;
        } else {
            throw new RuntimeException("Invalid 2FA code!");
        }
    }
}
