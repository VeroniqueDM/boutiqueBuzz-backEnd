package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.FashionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<FashionEvent, Long> {
    @Query("SELECT e.owner.id FROM FashionEvent e WHERE e.id = :eventId")
    Long findOwnerIdByEventId(@Param("eventId") Long eventId);

}