package com.api.boutiquebuzz.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class UserProfileDto {
    private Long id;
    private String name;
    private String username;
    private String email;

    public UserProfileDto(Long id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }
}
