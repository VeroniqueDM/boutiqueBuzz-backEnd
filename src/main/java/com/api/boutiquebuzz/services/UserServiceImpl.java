package com.api.boutiquebuzz.services;


import com.api.boutiquebuzz.domain.dtos.UserProfileDto;
import com.api.boutiquebuzz.domain.entities.UserEntity;
import com.api.boutiquebuzz.repositories.UserEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public void save(UserEntity user) {
        userEntityRepository.save(user);
    }
    @Override
    public UserProfileDto getOneById(Long id) {
        UserEntity user = userEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        return new UserProfileDto(user.getId(), user.getName(), user.getUsername(), user.getEmail());
    }
}
