package com.api.boutiquebuzz.web;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import com.api.boutiquebuzz.domain.dtos.CreateFashionItemRequestDTO;
import com.api.boutiquebuzz.domain.dtos.FashionItemResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateFashionItemRequestDTO;
import com.api.boutiquebuzz.services.FashionItemService;
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
@RequestMapping("/items")
public class FashionItemController {

    private final FashionItemService fashionItemService;

    @Autowired
    public FashionItemController(FashionItemService fashionItemService) {
        this.fashionItemService = fashionItemService;
    }

    @GetMapping
    public ResponseEntity<List<FashionItemResponseDTO>> getAllFashionItems() {
        List<FashionItemResponseDTO> fashionItemResponseDTOs = fashionItemService.getAllFashionItems();
        return ResponseEntity.ok(fashionItemResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFashionItemById(@PathVariable Long id) {
        try {
            FashionItemResponseDTO fashionItem = fashionItemService.getFashionItemById(id);
            return ResponseEntity.ok(fashionItem);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createFashionItem(@RequestBody @Valid CreateFashionItemRequestDTO fashionItemDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        FashionItemResponseDTO createdFashionItem = fashionItemService.createFashionItem(fashionItemDTO);
        return new ResponseEntity<>(createdFashionItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFashionItem(@PathVariable Long id, @RequestBody @Valid UpdateFashionItemRequestDTO fashionItemDTO, BindingResult bindingResult) {
        ResponseEntity<?> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;
        try {
            FashionItemResponseDTO updatedFashionItem = fashionItemService.updateFashionItem(id, fashionItemDTO);
            return ResponseEntity.ok(updatedFashionItem);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFashionItem(@PathVariable Long id) {
        try {
            FashionItemResponseDTO deletedFashionItem = fashionItemService.deleteFashionItem(id);
            return ResponseEntity.ok(deletedFashionItem);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}