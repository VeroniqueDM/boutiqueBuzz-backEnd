//package com.api.boutiquebuzz.domain.entities;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//@Entity
//@Table(name="user_entity")
//@Data
//@NoArgsConstructor
//public class UserEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column
//    private String name;
//    @Column
//    private String username;
//    @Columna
//    private String email;
//
//    @Column(name = "role")
//    @Enumerated(EnumType.STRING)
//    private UserRole role;
//
//    @Column(name = "source")
//    @Enumerated(EnumType.STRING)
//    private RegistrationSource source;
//    @Column
//    private String imageUrl;
//    public UserEntity(String email) {
//        this.email = email;
//    }
//
//    public UserEntity(String username, String email) {
//        this.username = username;
//        this.email = email;
//    }
//}
