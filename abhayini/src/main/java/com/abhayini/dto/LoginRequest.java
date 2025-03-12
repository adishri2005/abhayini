// Part 2 (for dto pkg): Login Request DTO

// src/main/java/com/abhayini/dto/LoginRequest.java

package com.abhayini.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest
{
    @Email
    @NotBlank                      //validation annot
    private String email;

    @NotBlank
    private String password;
}
