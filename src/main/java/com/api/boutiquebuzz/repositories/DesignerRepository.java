package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, Long> {
}