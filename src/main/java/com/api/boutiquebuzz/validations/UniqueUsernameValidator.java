package com.api.boutiquebuzz.validations;

import com.api.boutiquebuzz.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserRepository userRepository; // Inject your UserRepository

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        // Initialization, if needed
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && !userRepository.existsByUsername(username);
    }
}