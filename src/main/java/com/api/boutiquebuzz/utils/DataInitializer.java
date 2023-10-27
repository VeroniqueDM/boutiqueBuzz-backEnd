package com.api.boutiquebuzz.utils;

import com.api.boutiquebuzz.domain.dtos.*;
import com.api.boutiquebuzz.services.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer {
    private final DesignerService designerService;
    private final CollectionService collectionService;
    private final FashionItemService fashionItemService;
    private final NewsService newsService;
    private final EventService eventService;
    private final Faker faker;

    @Autowired
    public DataInitializer(DesignerService designerService, CollectionService collectionService, FashionItemService fashionItemService, NewsService newsService, EventService eventService) {
        this.designerService = designerService;
        this.collectionService = collectionService;
        this.fashionItemService = fashionItemService;
        this.newsService = newsService;
        this.eventService = eventService;
        this.faker = new Faker();
    }
@Transactional
public void initializeData() {
        // Generate 5 designers with 1 collection each
        for (int i = 0; i < 5; i++) {
            CreateDesignerRequestDTO designerDTO = new CreateDesignerRequestDTO();
            designerDTO.setName(faker.name().fullName());
            designerDTO.setEmail(faker.internet().emailAddress());
            designerDTO.setPhone(faker.phoneNumber().phoneNumber());
            DesignerResponseDTO createdDesigner = designerService.createDesigner(designerDTO);

            CreateCollectionRequestDTO collectionDTO = new CreateCollectionRequestDTO();
            collectionDTO.setName(faker.lorem().words(2).toString());
            collectionDTO.setDescription(faker.lorem().sentence());
            collectionDTO.setDesignerId(createdDesigner.getId());
            CollectionResponseDTO createdCollection = collectionService.createCollection(collectionDTO);
            // Generate 5 fashion items for each collection/designer
            generateFashionItems(createdDesigner, createdCollection);
            for (int k = 0; k < 5; k++) {
                CreateNewsRequestDTO newsDTO = new CreateNewsRequestDTO();
                newsDTO.setTitle(faker.lorem().words(4).toString());
                newsDTO.setContent(faker.lorem().paragraph(3));
                newsService.createNews(newsDTO);
            }

            for (int l = 0; l < 5; l++) {
                CreateEventRequestDTO eventDTO = new CreateEventRequestDTO();
                eventDTO.setTitle(faker.lorem().words(3).toString());
                eventDTO.setDescription(faker.lorem().sentence());
                eventService.createEvent(eventDTO);
            }
        }

    }

    private void generateFashionItems(DesignerResponseDTO createdDesigner, CollectionResponseDTO createdCollection) {
        for (int j = 0; j < 5; j++) {
            CreateFashionItemRequestDTO fashionItemDTO = new CreateFashionItemRequestDTO(); // Create a new fashionItemDTO
            fashionItemDTO.setName(faker.lorem().words(2).toString());
            fashionItemDTO.setDescription(faker.lorem().sentence());
//                fashionItemDTO.setCollectionId(createdCollection.getId());
            fashionItemDTO.setDesignerId(createdDesigner.getId());
//                fashionItemService.createFashionItem(fashionItemDTO);
            fashionItemService.createFashionItemWithCollection(createdCollection.getId(),fashionItemDTO);
//                collectionService.addItemToCollection()
        }
    }
}