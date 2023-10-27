package com.api.boutiquebuzz.domain.dtos.user;

import lombok.Data;

@Data
public class LoginUserDto {
    private String username;
    private String password;

    // Getters and setters
}