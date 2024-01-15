package com.api.boutiquebuzz.repositories;

import java.util.Optional;

import com.api.boutiquebuzz.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}