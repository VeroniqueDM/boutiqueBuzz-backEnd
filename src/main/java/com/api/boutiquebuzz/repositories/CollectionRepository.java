package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.DesignerCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<DesignerCollection, Long> {
}