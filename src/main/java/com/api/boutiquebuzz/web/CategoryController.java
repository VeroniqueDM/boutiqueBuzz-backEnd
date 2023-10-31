package com.api.boutiquebuzz.web;
import com.api.boutiquebuzz.domain.dtos.CategoryResponseDTO;
import com.api.boutiquebuzz.domain.dtos.CreateCategoryRequestDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateCategoryRequestDto;
import com.api.boutiquebuzz.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public CategoryResponseDTO createCategory(@RequestBody CreateCategoryRequestDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequestDto categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}