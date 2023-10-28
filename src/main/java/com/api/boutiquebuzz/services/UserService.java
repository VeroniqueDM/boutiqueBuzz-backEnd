package com.api.boutiquebuzz.services;


import com.api.boutiquebuzz.domain.dtos.user.UserRegisterFormDto;
import com.api.boutiquebuzz.domain.entities.user.UserEntity;
import com.api.boutiquebuzz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final EmailService emailService;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder
//            ,                     EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.emailService = emailService;
    }

    public void registerUser(UserRegisterFormDto registrationDTO) {

        UserEntity userEntity = new UserEntity().

                setFirstName(registrationDTO.getFirstName()).
                setLastName(registrationDTO.getLastName()).
                setEmail(registrationDTO.getEmail()).
                setPassword(passwordEncoder.encode(registrationDTO.getPassword()))
                .setActive(true)
                .setUsername(registrationDTO.getUsername());

        userRepository.save(userEntity);
//
//        emailService.sendRegistrationEmail(userEntity.getEmail(),
//                userEntity.getFirstName() + " " + userEntity.getLastName());

        //
    }
    public boolean existsByEmail(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }
    public UserEntity authenticate(String email, String password) {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent() && isPasswordValid(password, user.get().getPassword())) {
            return user.get();
        }

        return null;
    }
    private boolean isPasswordValid(String rawPassword, String hashedPassword) {
        // Implement your password validation logic (e.g., bcrypt, PBKDF2, etc.)
        // Compare rawPassword with hashedPassword securely
        // Return true if they match, false otherwise
        return passwordEncoder.matches(rawPassword, hashedPassword);

    }

//    private boolean passwordMatches(UserDetails userDetails, String providedPassword) {
//        // Check if the provided password matches the user's password
//    }
}