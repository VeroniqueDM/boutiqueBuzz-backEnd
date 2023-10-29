package com.api.boutiquebuzz.domain.dtos.user;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static com.api.boutiquebuzz.utils.InvalidMessages.EMPTY_EMAIL;
import static com.api.boutiquebuzz.utils.InvalidMessages.INVALID_EMAIL;

@Data
public class UserDetailsDto {
    private Long id;
    private String username;


    private String email;
}