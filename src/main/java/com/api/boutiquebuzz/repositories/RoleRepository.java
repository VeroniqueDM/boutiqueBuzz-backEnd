package com.api.boutiquebuzz.repositories;


import com.api.boutiquebuzz.domain.entities.user.UserRoleEntity;
import com.api.boutiquebuzz.utils.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRoleEntity, String> {
    Optional<UserRoleEntity> findByRole(UserRoleEnum role);
}
