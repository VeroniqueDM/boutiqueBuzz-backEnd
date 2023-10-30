package com.api.boutiquebuzz.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFashionItemRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
//    @JsonIgnore
//    private Long collectionId;
    @JsonIgnore
    private Long ownerId;
    private Long category;
}