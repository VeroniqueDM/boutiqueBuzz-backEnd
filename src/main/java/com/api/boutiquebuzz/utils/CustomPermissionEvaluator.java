package com.api.boutiquebuzz.utils;

import com.api.boutiquebuzz.domain.entities.AuthorOwnedEntity;
import com.api.boutiquebuzz.repositories.CollectionRepository;
import com.api.boutiquebuzz.repositories.EventRepository;
import com.api.boutiquebuzz.repositories.FashionItemRepository;
import com.api.boutiquebuzz.repositories.NewsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private final EventRepository fashionEventRepository;
    private final FashionItemRepository fashionItemRepository;
    private final CollectionRepository collectionRepository;
     private final NewsRepository newsRepository;

    public CustomPermissionEvaluator(EventRepository fashionEventRepository, FashionItemRepository fashionItemRepository, CollectionRepository collectionRepository, NewsRepository newsRepository) {
        this.fashionEventRepository = fashionEventRepository;
        this.fashionItemRepository = fashionItemRepository;
        this.collectionRepository = collectionRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof AuthorOwnedEntity) {
            AuthorOwnedEntity resource = (AuthorOwnedEntity) targetDomainObject;
            String currentUserEmail = ((User) authentication.getPrincipal()).getUsername();
            return currentUserEmail.equals(resource.getAuthorEmail());
        }
        return false;
    }


    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        // Check if the target type is one of your entity classes (e.g., DesignerCollection, FashionEvent, FashionItem, NewsArticle)
        if ("DesignerCollection".equals(targetType) || "FashionEvent".equals(targetType) || "FashionItem".equals(targetType) || "NewsArticle".equals(targetType)) {
            // Retrieve the entity based on its type and target ID
            Object resource = retrieveEntityByTypeAndId(targetType, targetId);

            if (resource != null) {
                // Get the email of the currently authenticated user
                String currentUserEmail = ((User) authentication.getPrincipal()).getUsername();

                if (resource instanceof AuthorOwnedEntity) {
                    AuthorOwnedEntity authorOwnedEntity = (AuthorOwnedEntity) resource;
                    // Compare the user's email with the resource's author email
                    return currentUserEmail.equals(authorOwnedEntity.getAuthorEmail());
                }
            }
        }

        return false;
    }

    private Object retrieveEntityByTypeAndId(String targetType, Serializable targetId) {
        // Implement logic to retrieve the entity based on its type and ID
        // You may need to have separate repository methods for each entity type
        // and call the appropriate repository based on the targetType.

        if ("DesignerCollection".equals(targetType)) {
            return collectionRepository.findById((Long) targetId).orElse(null);
        } else if ("FashionEvent".equals(targetType)) {
            return fashionEventRepository.findById((Long) targetId).orElse(null);
        } else if ("FashionItem".equals(targetType)) {
            return fashionItemRepository.findById((Long) targetId).orElse(null);
        } else if ("NewsArticle".equals(targetType)) {
            return newsRepository.findById((Long) targetId).orElse(null);
        }

        return null;
    }
}