// Part 1 (for dto pkg): Registration Request DTO

// DTO: Data Transfer Object is used to transfer data between software application subsystems.
// DTOs are used to encapsulate the data that needs to be transferred between the layers of the application.
// DTOs help in separating the API contract from your internal models.

package com.abhayini.dto;

import com.abhayini.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data                       // lombok annotation- generate boilerplate code (getters, setters, toString, etc.)
public class RegisterRequest
{
    @NotBlank                // validation annot to ensure the field is not blank
    private String username;

    @Email                   // validation annot to ensure the field is a valid email address
    @NotBlank
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters") // validation annot to ensure the field meets the size constraint
    private String password;                                      // must be at least 6 characters long

    private UserRole role;                    // role of the user, represented as an enum
}