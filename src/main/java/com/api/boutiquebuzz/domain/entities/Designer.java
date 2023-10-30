//package com.api.boutiquebuzz.domain.entities;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "designers")
//public class Designer extends BaseEntity {
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String phone;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    public Designer(String name, String email, String phone) {
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//    }
//}