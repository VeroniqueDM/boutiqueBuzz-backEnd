package com.api.boutiquebuzz.services;

import com.api.boutiquebuzz.domain.entities.Category;
import com.api.boutiquebuzz.domain.entities.DesignerCollection;
import com.api.boutiquebuzz.domain.entities.User;
import com.api.boutiquebuzz.repositories.CategoryRepository;
import com.api.boutiquebuzz.repositories.CollectionRepository;
import com.api.boutiquebuzz.repositories.UserRepository;
import com.api.boutiquebuzz.utils.ErrorConstants;
import com.api.boutiquebuzz.domain.dtos.CreateFashionItemRequestDTO;
import com.api.boutiquebuzz.domain.dtos.FashionItemResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateFashionItemRequestDTO;
import com.api.boutiquebuzz.domain.entities.FashionItem;
import com.api.boutiquebuzz.repositories.FashionItemRepository;
import com.api.boutiquebuzz.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FashionItemServiceImpl implements FashionItemService {
    private final FashionItemRepository fashionItemRepository;
    private final CollectionRepository collectionRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public FashionItemServiceImpl(FashionItemRepository fashionItemRepository, CollectionRepository collectionRepository,
                                  CategoryRepository categoryRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.fashionItemRepository = fashionItemRepository;
        this.collectionRepository = collectionRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
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

//    @Override
//    public FashionItemResponseDTO createFashionItem(CreateFashionItemRequestDTO fashionItemDTO) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//
//        FashionItem fashionItem = modelMapper.map(fashionItemDTO, FashionItem.class);
//        Category category = categoryRepository.findById(fashionItemDTO.getCategory()).get();
//        if (category == null) {
//            // Handle the case when the category doesn't exist or throw an exception
//            // create a new category and set its name
//            return null;
//        }
//        fashionItem.setCategory(category);
//
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        fashionItem.setCreatedAt(currentDateTime);
//        fashionItem.setUpdatedAt(currentDateTime);
//        FashionItem savedFashionItem = fashionItemRepository.save(fashionItem);
//        return mapFashionItemToDTO(savedFashionItem);
//    }

@Override
public FashionItemResponseDTO createFashionItem(CreateFashionItemRequestDTO fashionItemDTO) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {
        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();

        User owner = userRepository.findByEmail(userDetails.getAttribute("email")).get();

        if (owner == null) {
            return null;
        }

        FashionItem fashionItem = modelMapper.map(fashionItemDTO, FashionItem.class);
        Category category = categoryRepository.findById(fashionItemDTO.getCategory()).orElse(null);

        if (category == null) {
            return null;
        }

        fashionItem.setCategory(category);
        fashionItem.setOwner(owner);

        LocalDateTime currentDateTime = LocalDateTime.now();
        fashionItem.setCreatedAt(currentDateTime);
        fashionItem.setUpdatedAt(currentDateTime);
        FashionItem savedFashionItem = fashionItemRepository.save(fashionItem);
        return mapFashionItemToDTO(savedFashionItem);
    }

    return null;
}
    @Override
    public FashionItemResponseDTO createFashionItemWithCollection(Long collectionId, CreateFashionItemRequestDTO fashionItemDTO) {
        Optional<DesignerCollection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()) {
            FashionItem fashionItem = modelMapper.map(fashionItemDTO, FashionItem.class);
            LocalDateTime currentDateTime = LocalDateTime.now();
            fashionItem.setCreatedAt(currentDateTime);
            fashionItem.setUpdatedAt(currentDateTime);
//            fashionItem.setCollection(collectionOptional.get());
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

        if (fashionItem.getOwner() != null) {
            responseDTO.setDesignerName(fashionItem.getOwner().getName());
//            responseDTO.setDesignerEmail(fashionItem.getOwner().getEmail());
//            responseDTO.setDesignerPhone(fashionItem.getDesigner().getPhone());
            responseDTO.setOwnerId(Long.valueOf(fashionItem.getOwner().getId()));
        }

        return responseDTO;
    }
    @Override
    public List<FashionItemResponseDTO> getFashionItemsByCategoryId(Long categoryId) {
        // Retrieve fashion items by category ID
        List<FashionItem> fashionItems = fashionItemRepository.findByCategoryId(categoryId);
        return fashionItems.stream()
                .map(this::mapFashionItemToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FashionItemResponseDTO> getFashionItemsByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            return Collections.emptyList();
        }

        List<FashionItem> fashionItems = fashionItemRepository.findByCategoryName(categoryName);
        return fashionItems.stream()
                .map(this::mapFashionItemToDTO)
                .collect(Collectors.toList());
    }

}