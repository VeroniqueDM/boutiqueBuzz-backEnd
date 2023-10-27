package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.FashionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<FashionEvent, Long> {
}