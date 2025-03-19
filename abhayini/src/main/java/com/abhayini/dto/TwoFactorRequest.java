// Part 3 (for dto pkg): Two Factor Request DTO

// src/main/java/com/abhayini/dto/TwoFactorRequest.java

package com.abhayini.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TwoFactorRequest
{
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String code;
}
