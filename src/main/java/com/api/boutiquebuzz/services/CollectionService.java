package com.api.boutiquebuzz.services;

import com.api.boutiquebuzz.domain.dtos.CollectionResponseDTO;
import com.api.boutiquebuzz.domain.dtos.CreateCollectionRequestDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateCollectionRequestDTO;
import com.api.boutiquebuzz.domain.entities.DesignerCollection;
import com.api.boutiquebuzz.domain.entities.FashionItem;

import java.util.List;

public interface CollectionService {
    List<CollectionResponseDTO> getAllCollections();
    CollectionResponseDTO getCollectionById(Long collectionId);
    CollectionResponseDTO createCollection(CreateCollectionRequestDTO collectionDTO);
    CollectionResponseDTO updateCollection(Long id, UpdateCollectionRequestDTO updateCollectionDTO);
    CollectionResponseDTO deleteCollection(Long collectionId);

//    DesignerCollection addItemToCollection(DesignerCollection collection, FashionItem fashionItem);
}