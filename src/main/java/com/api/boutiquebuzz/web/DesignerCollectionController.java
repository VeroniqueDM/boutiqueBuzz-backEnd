package com.api.boutiquebuzz.web;
import com.api.boutiquebuzz.domain.dtos.UpdateCollectionRequestDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import com.api.boutiquebuzz.domain.dtos.CollectionResponseDTO;
import com.api.boutiquebuzz.domain.dtos.CreateCollectionRequestDTO;
import com.api.boutiquebuzz.services.CollectionService;
import com.api.boutiquebuzz.utils.ErrorUtil;
import com.api.boutiquebuzz.utils.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin

@RestController
@RequestMapping("/collections")
public class DesignerCollectionController {

    private final CollectionService designerCollectionService;

    @Autowired
    public DesignerCollectionController(CollectionService designerCollectionService) {
        this.designerCollectionService = designerCollectionService;
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponseDTO>> getAllDesignerCollections() {
        List<CollectionResponseDTO> designerCollectionResponseDTOs = designerCollectionService.getAllCollections();
        return ResponseEntity.ok(designerCollectionResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDesignerCollectionById(@PathVariable Long id) {
        try {
            CollectionResponseDTO designerCollection = designerCollectionService.getCollectionById(id);
            return ResponseEntity.ok(designerCollection);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createDesignerCollection(@RequestBody @Valid CreateCollectionRequestDTO designerCollectionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        CollectionResponseDTO createdDesignerCollection = designerCollectionService.createCollection(designerCollectionDTO);
        return new ResponseEntity<>(createdDesignerCollection, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDesignerCollection(@PathVariable Long id, @RequestBody @Valid UpdateCollectionRequestDTO designerCollectionDTO, BindingResult bindingResult) {
        ResponseEntity<?> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;
        try {
            CollectionResponseDTO updatedDesignerCollection = designerCollectionService.updateCollection(id, designerCollectionDTO);
            return ResponseEntity.ok(updatedDesignerCollection);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDesignerCollection(@PathVariable Long id) {
        try {
            CollectionResponseDTO deletedDesignerCollection = designerCollectionService.deleteCollection(id);
            return ResponseEntity.ok(deletedDesignerCollection);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

