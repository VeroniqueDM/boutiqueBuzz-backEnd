package com.api.boutiquebuzz.services;
import com.api.boutiquebuzz.domain.dtos.CollectionResponseDTO;
import com.api.boutiquebuzz.domain.dtos.CreateCollectionRequestDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateCollectionRequestDTO;
import com.api.boutiquebuzz.domain.entities.DesignerCollection;
import com.api.boutiquebuzz.domain.entities.UserEntity;
import com.api.boutiquebuzz.repositories.CollectionRepository;
import com.api.boutiquebuzz.repositories.UserEntityRepository;
import com.api.boutiquebuzz.utils.ErrorConstants;

import com.api.boutiquebuzz.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserEntityRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CollectionServiceImpl(CollectionRepository collectionRepository, UserEntityRepository userRepository, ModelMapper modelMapper) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CollectionResponseDTO> getAllCollections() {
        List<DesignerCollection> collections = collectionRepository.findAll();
        return collections.stream()
                .map(collection -> modelMapper.map(collection, CollectionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CollectionResponseDTO getCollectionById(Long collectionId) {
        Optional<DesignerCollection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()) {
            return modelMapper.map(collectionOptional.get(), CollectionResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.COLLECTION_NOT_FOUND_MESSAGE, collectionId));
        }
    }

//    @Override
//    public CollectionResponseDTO createCollection(CreateCollectionRequestDTO collectionDTO) {
//        DesignerCollection collection = modelMapper.map(collectionDTO, DesignerCollection.class);
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        collection.setCreatedAt(currentDateTime);
//        collection.setUpdatedAt(currentDateTime);
//        DesignerCollection savedCollection = collectionRepository.save(collection);
//        return modelMapper.map(savedCollection, CollectionResponseDTO.class);
//    }
@Override
public CollectionResponseDTO createCollection(CreateCollectionRequestDTO collectionDTO) {
    // Retrieve the authenticated user (you need to have user authentication in place)
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // You should have a UserDetails implementation that includes your user details
    if (authentication != null) {
        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();

        // Use the user details to set the owner of the collection
        UserEntity owner = userRepository.findByEmail(userDetails.getAttribute("email")).orElse(null);

        if (owner == null) {
            // Handle the case when the owner doesn't exist or throw an exception
            return null;
        }

        DesignerCollection collection = modelMapper.map(collectionDTO, DesignerCollection.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        collection.setCreatedAt(currentDateTime);
        collection.setUpdatedAt(currentDateTime);

        // Set the owner of the collection
        collection.setOwner(owner);

        DesignerCollection savedCollection = collectionRepository.save(collection);
        return modelMapper.map(savedCollection, CollectionResponseDTO.class);
    }

    return null; // Handle the case when there's no authenticated user
}
    @Override
    public CollectionResponseDTO updateCollection(Long collectionId, UpdateCollectionRequestDTO updateCollectionDTO) {
        Optional<DesignerCollection> collectionOptional = collectionRepository.findById(collectionId);
        if (collectionOptional.isPresent()) {
            DesignerCollection existingCollection = collectionOptional.get();

            if (hasUpdates(existingCollection, updateCollectionDTO)) {
                existingCollection.setUpdatedAt(LocalDateTime.now());
            }

            modelMapper.map(updateCollectionDTO, existingCollection);
            DesignerCollection updatedCollection = collectionRepository.save(existingCollection);

            return modelMapper.map(updatedCollection, CollectionResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.COLLECTION_NOT_FOUND_MESSAGE, collectionId));
        }
    }

    @Override
    public CollectionResponseDTO deleteCollection(Long collectionId) {
        DesignerCollection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.COLLECTION_NOT_FOUND_MESSAGE, collectionId)));

        collectionRepository.delete(collection);

        return modelMapper.map(collection, CollectionResponseDTO.class);
    }

    private boolean hasUpdates(DesignerCollection existingCollection, UpdateCollectionRequestDTO updateCollectionDTO) {
        boolean hasUpdates = !existingCollection.getName().equals(updateCollectionDTO.getName()) ||
                !existingCollection.getDescription().equals(updateCollectionDTO.getDescription());

        return hasUpdates;
    }
//@Override
//    public DesignerCollection addItemToCollection(CollectionResponseDTO collection, CreateFashionItemRequestDTO fashionItem) {
//        collection.addItem(fashionItem);
//        return collectionRepository.save(collection);
//    }
}