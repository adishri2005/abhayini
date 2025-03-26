// Part 1 (for controller pkg): Authentication Controller

// expose REST endpoints for user registration and login.

// this is the controller class for the authentication endpoints.
// it contains two endpoints, one for registering a new user and another for logging in an existing user.
// the controller class uses the AuthService class to handle the business logic for registration and login.

// src/main/java/com/abhayini/controller/AuthController.java


//
//package com.abhayini.controller;
//
//import com.abhayini.dto.LoginRequest;
//import com.abhayini.dto.RegisterRequest;
//import com.abhayini.service.AuthService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController                     // indicates that this class is a REST controller
//@RequestMapping("/auth")         // base url for all endpoints in this controller
//@RequiredArgsConstructor            // lombok annot to generate a constructor with required arguments
//public class AuthController
//{
//    private final AuthService authService;              // service to handle authentication logic
//
//    @PostMapping("/register")                // endpoint for user registration
//    public String register(@Valid @RequestBody RegisterRequest request)
//    {
//        return authService.register(request); // delegate registration logic to AuthService
//    }
//
//    @PostMapping("/login")                  // endpoint for user login
//    public String login(@Valid @RequestBody LoginRequest request)
//    {
//        return authService.login(request);              // delegate login logic to AuthService
//    }
//}


package com.abhayini.controller;

import com.abhayini.dto.LoginRequest;
import com.abhayini.dto.RegisterRequest;
import com.abhayini.dto.TwoFactorRequest;
import com.abhayini.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/verify2fa")
    public String verifyTwoFactor(@Valid @RequestBody TwoFactorRequest request) {
        return authService.verifyTwoFactor(request.getEmail(), request.getCode());
    }
}