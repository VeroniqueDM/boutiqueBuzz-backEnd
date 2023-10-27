package com.api.boutiquebuzz.services;


import com.api.boutiquebuzz.domain.dtos.user.UserRegisterFormDto;
import com.api.boutiquebuzz.domain.entities.user.UserEntity;
import com.api.boutiquebuzz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        userRepository.save(userEntity);
//
//        emailService.sendRegistrationEmail(userEntity.getEmail(),
//                userEntity.getFirstName() + " " + userEntity.getLastName());

        //
    }

}