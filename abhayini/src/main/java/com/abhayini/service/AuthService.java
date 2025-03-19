// Part 1 (for service pkg): authentication service
// this service will handle user registration and login.
// note the use of a password encoder for security.

package com.abhayini.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abhayini.dto.AuthResponse;
import com.abhayini.dto.LoginRequest;
import com.abhayini.dto.RegisterRequest;
import com.abhayini.exception.InvalidCredentialsException;
import com.abhayini.exception.UserAlreadyExistsException;
import com.abhayini.model.User;
import com.abhayini.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service // indicates that this class is a service component in the Spring context
@RequiredArgsConstructor // lombok annot to generate a constructor with required args
public class AuthService
{
    private final UserRepository userRepository;          // repo to interact with the user entity
    private final PasswordEncoder passwordEncoder;        // password encoder for hashing passwords
    private final JwtTokenProvider jwtTokenProvider;      // JWT token provider for generating tokens

    // method to handle user registration
    public AuthResponse register(RegisterRequest request)
    {
        if (request.getRole() == null) {
            throw new IllegalArgumentException("User role is required");
        }
        // check if a user with the given email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent())
        {
            throw new UserAlreadyExistsException("User with this email already exists!");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        // hash the password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        user = userRepository.save(user); // save the new user to the database
        
        // Generate JWT token for the newly registered user
        String token = jwtTokenProvider.generateToken(user);
        
        return new AuthResponse(token, user.getUsername());
    }

    // method to handle user login
    public AuthResponse login(LoginRequest request)
    {
        // find the user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("User not found with email: " + request.getEmail()));

        // check if the provided password matches the stored hashed password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
        {
            throw new InvalidCredentialsException("Invalid credentials!");
        }
        
        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user);
        
        return new AuthResponse(token, user.getUsername());
    }
}