package com.api.boutiquebuzz.services;

import com.api.boutiquebuzz.domain.entities.DesignerCollection;
import com.api.boutiquebuzz.repositories.CollectionRepository;
import com.api.boutiquebuzz.utils.ErrorConstants;
import com.api.boutiquebuzz.domain.dtos.CreateFashionItemRequestDTO;
import com.api.boutiquebuzz.domain.dtos.FashionItemResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateFashionItemRequestDTO;
import com.api.boutiquebuzz.domain.entities.FashionItem;
import com.api.boutiquebuzz.repositories.FashionItemRepository;
import com.api.boutiquebuzz.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FashionItemServiceImpl implements FashionItemService {
    private final FashionItemRepository fashionItemRepository;
    private final CollectionRepository collectionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FashionItemServiceImpl(FashionItemRepository fashionItemRepository, CollectionRepository collectionRepository, ModelMapper modelMapper) {
        this.fashionItemRepository = fashionItemRepository;
        this.collectionRepository = collectionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FashionItemResponseDTO> getAllFashionItems() {
        List<FashionItem> fashionItems = fashionItemRepository.findAll();
        return fashionItems.stream()
                .map(item -> mapFashionItemToDTO(item))
                .collect(Collectors.toList());
    }

    @Override
    public FashionItemResponseDTO getFashionItemById(Long fashionItemId) {
        Optional<FashionItem> fashionItemOptional = fashionItemRepository.findById(fashionItemId);
        if (fashionItemOptional.isPresent()) {
//            return modelMapper.map(fashionItemOptional.get(), FashionItemResponseDTO.class);
            return mapFashionItemToDTO(fashionItemOptional.get());
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.ITEM_NOT_FOUND_MESSAGE, fashionItemId));
        }
    }

    @Override
    public FashionItemResponseDTO createFashionItem(CreateFashionItemRequestDTO fashionItemDTO) {
        FashionItem fashionItem = modelMapper.map(fashionItemDTO, FashionItem.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        fashionItem.setCreatedAt(currentDateTime);
        fashionItem.setUpdatedAt(currentDateTime);
        FashionItem savedFashionItem = fashionItemRepository.save(fashionItem);
        return mapFashionItemToDTO(savedFashionItem);
    }
    @Override
    public FashionItemResponseDTO createFashionItemWithCollection(Long collectionId, CreateFashionItemRequestDTO fashionItemDTO) {
        Optional<DesignerCollection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()) {
            FashionItem fashionItem = modelMapper.map(fashionItemDTO, FashionItem.class);
            LocalDateTime currentDateTime = LocalDateTime.now();
            fashionItem.setCreatedAt(currentDateTime);
            fashionItem.setUpdatedAt(currentDateTime);
            fashionItem.setCollection(collectionOptional.get());
            FashionItem savedFashionItem = fashionItemRepository.save(fashionItem);
            return  mapFashionItemToDTO(savedFashionItem);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.COLLECTION_NOT_FOUND_MESSAGE, collectionId));
        }
    }
    @Override
    public FashionItemResponseDTO updateFashionItem(Long fashionItemId, UpdateFashionItemRequestDTO updateFashionItemDTO) {
        Optional<FashionItem> fashionItemOptional = fashionItemRepository.findById(fashionItemId);
        if (fashionItemOptional.isPresent()) {
            FashionItem existingFashionItem = fashionItemOptional.get();

            if (hasUpdates(existingFashionItem, updateFashionItemDTO)) {
                existingFashionItem.setUpdatedAt(LocalDateTime.now());
            }

            modelMapper.map(updateFashionItemDTO, existingFashionItem);
            FashionItem updatedFashionItem = fashionItemRepository.save(existingFashionItem);

            return modelMapper.map(updatedFashionItem, FashionItemResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.ITEM_NOT_FOUND_MESSAGE, fashionItemId));
        }
    }

    @Override
    public FashionItemResponseDTO deleteFashionItem(Long fashionItemId) {
        FashionItem fashionItem = fashionItemRepository.findById(fashionItemId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.ITEM_NOT_FOUND_MESSAGE, fashionItemId)));

        fashionItemRepository.delete(fashionItem);

        return modelMapper.map(fashionItem, FashionItemResponseDTO.class);
    }

    private boolean hasUpdates(FashionItem existingFashionItem, UpdateFashionItemRequestDTO updateFashionItemDTO) {
        boolean hasUpdates = !existingFashionItem.getName().equals(updateFashionItemDTO.getName()) ||
                !existingFashionItem.getDescription().equals(updateFashionItemDTO.getDescription());

        return hasUpdates;
    }
    public FashionItemResponseDTO mapFashionItemToDTO(FashionItem fashionItem) {
        FashionItemResponseDTO responseDTO = modelMapper.map(fashionItem, FashionItemResponseDTO.class);

        if (fashionItem.getDesigner() != null) {
            responseDTO.setDesignerName(fashionItem.getDesigner().getName());
            responseDTO.setDesignerEmail(fashionItem.getDesigner().getEmail());
            responseDTO.setDesignerPhone(fashionItem.getDesigner().getPhone());
        }

        return responseDTO;
    }
}