package com.api.boutiquebuzz.domain.entities;

import com.api.boutiquebuzz.user.User;
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
public class FashionItem extends BaseEntity implements AuthorOwnedEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private String imageUrl;
    //    @ManyToOne
//    @JoinColumn(name = "designer_id", nullable = false)
//    private Designer designer;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "collection_id")
//    private DesignerCollection collection;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public FashionItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getAuthorEmail() {
        if (this.owner != null) {
            return this.owner.getEmail();
        }
        return "Unknown";
    }
}