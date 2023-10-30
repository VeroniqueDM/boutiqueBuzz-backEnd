package com.api.boutiquebuzz.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "collections")
public class DesignerCollection extends BaseEntity {
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

//    @OneToMany(mappedBy = "collection",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FashionItem> fashionItems;

    public DesignerCollection(String name, String description) {
        this.name = name;
        this.description = description;
//        this.fashionItems = new ArrayList<>();
    }
//    public void addItem(FashionItem fashionItem) {
//        fashionItems.add(fashionItem);
//        fashionItem.setCollection(this);
//    }
}