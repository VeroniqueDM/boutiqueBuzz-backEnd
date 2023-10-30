package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.Category;
import com.api.boutiquebuzz.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

}