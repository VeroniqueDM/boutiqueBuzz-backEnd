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
public class DesignerCollection extends BaseEntity implements AuthorOwnedEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ElementCollection
    @CollectionTable(name = "collection_images", joinColumns = @JoinColumn(name = "collection_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();
//    @ManyToOne
//    @JoinColumn(name = "designer_id", nullable = false)
//    private UserEntity designer;

//    @OneToMany(mappedBy = "collection",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FashionItem> fashionItems;
@ManyToOne
@JoinColumn(name = "owner_id", nullable = false)
private UserEntity owner;
    public DesignerCollection(String name, String description) {
        this.name = name;
        this.description = description;
        this.imageUrls = new ArrayList<>();
    }

    @Override
    public String getAuthorEmail() {
        if (this.owner != null) {
            return this.owner.getEmail();
        }
        // Return an appropriate value (e.g., a placeholder) if owner is null
        return "Unknown";
    }
//    public void addItem(FashionItem fashionItem) {
//        fashionItems.add(fashionItem);
//        fashionItem.setCollection(this);
//    }
}