package com.api.boutiquebuzz.domain.entities;


import com.api.boutiquebuzz.domain.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "fashion_news")
public class NewsArticle extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
//    @ManyToOne
//    @JoinColumn(name = "designer_id", nullable = false)
//    private Designer designer;
@ManyToOne(optional = false)
private UserEntity owner;
    public NewsArticle(String title, String content, LocalDateTime publishedAt) {
        this.title = title;
        this.content = content;
        this.publishedAt = publishedAt;
    }

}