package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.FashionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FashionItemRepository extends JpaRepository<FashionItem, Long> {
}