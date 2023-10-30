package com.api.boutiquebuzz.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateFashionItemRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
    @JsonIgnore
    private Long ownerId;
    private Long category;
    private String imageUrl; // Add the image URL attribute

}