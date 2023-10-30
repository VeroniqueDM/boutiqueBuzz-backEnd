package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

//    UserEntity findByUsername(String username);

//    UserEntity findByUsername(String username);
}
