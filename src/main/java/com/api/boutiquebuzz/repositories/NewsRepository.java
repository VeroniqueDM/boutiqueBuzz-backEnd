package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsArticle, Long> {
}