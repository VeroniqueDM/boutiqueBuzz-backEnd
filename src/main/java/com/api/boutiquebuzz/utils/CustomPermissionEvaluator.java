//package com.api.boutiquebuzz.utils;
//
//import com.api.boutiquebuzz.domain.entities.AuthorOwnedEntity;
//
//import com.api.boutiquebuzz.repositories.CollectionRepository;
//import com.api.boutiquebuzz.repositories.EventRepository;
//import com.api.boutiquebuzz.repositories.FashionItemRepository;
//import com.api.boutiquebuzz.repositories.NewsRepository;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.access.PermissionEvaluator;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//
//@Component
//public class CustomPermissionEvaluator implements PermissionEvaluator {
//    private final EventRepository fashionEventRepository;
//    private final FashionItemRepository fashionItemRepository;
//    private final CollectionRepository collectionRepository;
//    private final NewsRepository newsRepository;
//
//    public CustomPermissionEvaluator(EventRepository fashionEventRepository, FashionItemRepository fashionItemRepository, CollectionRepository collectionRepository, NewsRepository newsRepository) {
//        this.fashionEventRepository = fashionEventRepository;
//        this.fashionItemRepository = fashionItemRepository;
//        this.collectionRepository = collectionRepository;
//        this.newsRepository = newsRepository;
//    }
//
////    @Override
////    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
////        if (targetDomainObject instanceof AuthorOwnedEntity) {
////            AuthorOwnedEntity resource = (AuthorOwnedEntity) targetDomainObject;
//////            String currentUserEmail = ((User) authentication.getPrincipal()).getUsername();
////            DefaultOAuth2User customUser = (DefaultOAuth2User) authentication.getPrincipal();
////            String currentUserEmail = customUser.getAttribute("email");
////            return currentUserEmail.equals(resource.getAuthorEmail());
////        }
////        return false;
////    }
//
//    @Override
//    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
//        if (targetDomainObject instanceof AuthorOwnedEntity) {
//            AuthorOwnedEntity resource = (AuthorOwnedEntity) targetDomainObject;
//
//            System.out.println("Checking permissions for resource of class: " + resource.getClass().getName());
//            System.out.println("Resource details: " + resource);
//
//            DefaultOAuth2User customUser = (DefaultOAuth2User) authentication.getPrincipal();
//            String currentUserEmail = customUser.getAttribute("email");
//
//            System.out.println("Current user email: " + currentUserEmail);
//            System.out.println("Resource author email: " + resource.getAuthorEmail());
//
//            boolean hasPermission = currentUserEmail.equals(resource.getAuthorEmail());
//            System.out.println("Permission check result: " + hasPermission);
//
//            return hasPermission;
//        }
//
//        System.out.println("Unknown targetDomainObject type: " + targetDomainObject.getClass().getName());
//        return false;
//    }
//    //    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
////        if ("DesignerCollection".equals(targetType) || "FashionEvent".equals(targetType) || "FashionItem".equals(targetType) || "NewsArticle".equals(targetType)) {
////            Object resource = retrieveEntityByTypeAndId(targetType, targetId);
////
////            if (resource != null) {
////                String currentUserEmail = ((User) authentication.getPrincipal()).getUsername();
////
////                if (resource instanceof AuthorOwnedEntity) {
////                    AuthorOwnedEntity authorOwnedEntity = (AuthorOwnedEntity) resource;
////                    return currentUserEmail.equals(authorOwnedEntity.getAuthorEmail());
////                }
////            }
////        }
////
////        return false;
////    }
//    @Override
//    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
//        System.out.println("Checking permissions for targetType: " + targetType + ", targetId: " + targetId);
//
//        System.out.println(permission);
//        if ("DesignerCollection".equals(targetType) || "FashionEvent".equals(targetType) || "FashionItem".equals(targetType) || "NewsArticle".equals(targetType)) {
//            Object resource = retrieveEntityByTypeAndId(targetType, targetId);
//            System.out.println("Retrieved resource: " + resource);
//
//            if (resource != null) {
////                String currentUserEmail = ((User) authentication.getPrincipal()).getUsername();
//                DefaultOAuth2User customUser = (DefaultOAuth2User) authentication.getPrincipal();
////                String currentUserEmail = customUser.getEmail();
//                String currentUserEmail = customUser.getAttribute("email");
//                System.out.println("Current user email: " + currentUserEmail);
//
//                if (resource instanceof AuthorOwnedEntity) {
//                    AuthorOwnedEntity authorOwnedEntity = (AuthorOwnedEntity) resource;
//                    System.out.println("Resource author email: " + authorOwnedEntity.getAuthorEmail());
//
//                    return currentUserEmail.equals(authorOwnedEntity.getAuthorEmail());
//                }
//            }
//        }
//
//        return false;
//    }
//
//    private Object retrieveEntityByTypeAndId(String targetType, Serializable targetId) {
//
//        if ("DesignerCollection".equals(targetType)) {
//            return collectionRepository.findById((Long) targetId).orElse(null);
//        } else if ("FashionEvent".equals(targetType)) {
//            return fashionEventRepository.findById((Long) targetId).orElse(null);
//        } else if ("FashionItem".equals(targetType)) {
//            return fashionItemRepository.findById((Long) targetId).orElse(null);
//        } else if ("NewsArticle".equals(targetType)) {
//            return newsRepository.findById((Long) targetId).orElse(null);
//        }
//
//        return null;
//    }
//}