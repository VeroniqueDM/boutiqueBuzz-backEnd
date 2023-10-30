package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.FashionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<FashionEvent, Long> {
    @Query("SELECT e FROM FashionEvent e WHERE LOWER(e.title) LIKE %:keyword% OR LOWER(e.description) LIKE %:keyword%")
    List<FashionEvent> searchEvents(@Param("keyword") String keyword);
}