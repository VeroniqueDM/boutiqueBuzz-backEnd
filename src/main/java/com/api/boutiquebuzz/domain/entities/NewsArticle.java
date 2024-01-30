package com.api.boutiquebuzz.domain.entities;


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
public class NewsArticle extends BaseEntity implements AuthorOwnedEntity {
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
@ManyToOne
@JoinColumn(name = "owner_id", nullable = false)
private User owner;
    public NewsArticle(String title, String content, LocalDateTime publishedAt) {
        this.title = title;
        this.content = content;
        this.publishedAt = publishedAt;
    }
    @Override
    public String getAuthorEmail() {
        if (this.owner != null) {
            return this.owner.getEmail();
        }
        return "Unknown";
    }
}