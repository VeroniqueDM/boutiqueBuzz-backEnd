package com.api.boutiquebuzz.services;

import com.api.boutiquebuzz.domain.dtos.CreateFashionItemRequestDTO;
import com.api.boutiquebuzz.domain.dtos.FashionItemResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateFashionItemRequestDTO;

import java.util.List;

public interface FashionItemService {
    List<FashionItemResponseDTO> getAllFashionItems();
    FashionItemResponseDTO getFashionItemById(Long fashionItemId);
    FashionItemResponseDTO createFashionItem(CreateFashionItemRequestDTO fashionItemDTO);

    FashionItemResponseDTO createFashionItemWithCollection(Long collectionId, CreateFashionItemRequestDTO fashionItemDTO);

    FashionItemResponseDTO updateFashionItem(Long id, UpdateFashionItemRequestDTO updateFashionItemDTO);
    FashionItemResponseDTO deleteFashionItem(Long fashionItemId);

}