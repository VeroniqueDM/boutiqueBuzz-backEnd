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
@Table(name = "fashion_items")
public class FashionItem extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "designer_id", nullable = false)
    private Designer designer;
@ManyToOne(optional = false)
private UserEntity owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private DesignerCollection collection;
    public FashionItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

}