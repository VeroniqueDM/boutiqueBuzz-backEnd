package com.api.boutiquebuzz.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_entity")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
//    private String username;

    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private RegistrationSource source;

    public UserEntity(String email) {
        this.email = email;
    }
}
