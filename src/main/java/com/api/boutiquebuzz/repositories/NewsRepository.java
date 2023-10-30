package com.api.boutiquebuzz.repositories;

import com.api.boutiquebuzz.domain.entities.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsArticle, Long> {
    @Query("SELECT n FROM NewsArticle n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword%")
    List<NewsArticle> searchNews(@Param("keyword") String keyword);
}