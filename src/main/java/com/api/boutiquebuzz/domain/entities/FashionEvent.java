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
@Table(name = "fashion_events")
public class FashionEvent extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
//    @ManyToOne
//    @JoinColumn(name = "designer_id", nullable = false)
//    private Designer designer;

    public FashionEvent(String title, String description, LocalDateTime eventDate) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
    }

}