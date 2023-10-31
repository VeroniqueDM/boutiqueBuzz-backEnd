package com.api.boutiquebuzz.domain.dtos;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String role;
    private String source;
    private String imageUrl;


    public UserDTO() {
    }

    public UserDTO(Long id, String name, String username, String email, String role, String source) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
        this.source = source;
    }
}