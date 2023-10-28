package com.api.boutiquebuzz.domain.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static com.api.boutiquebuzz.utils.InvalidMessages.EMPTY_EMAIL;
import static com.api.boutiquebuzz.utils.InvalidMessages.INVALID_EMAIL;

@Data
public class LoginUserDto {
    @NotBlank
    private String username;

    @NotBlank(message = EMPTY_EMAIL)
    @Email(message = INVALID_EMAIL)
    private String email;
    @NotBlank(message = "You must enter a password!")

    private String password;

    // Getters and setters
}