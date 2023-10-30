package com.api.boutiquebuzz.services;
import com.api.boutiquebuzz.domain.dtos.CategoryResponseDTO;
import com.api.boutiquebuzz.domain.dtos.CreateCategoryRequestDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateCategoryRequestDto;
import com.api.boutiquebuzz.domain.entities.Category;
import com.api.boutiquebuzz.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO updateCategory(Long id, UpdateCategoryRequestDto categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryResponseDTO.class);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}