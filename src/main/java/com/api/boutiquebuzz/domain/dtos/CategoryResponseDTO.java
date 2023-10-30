package com.api.boutiquebuzz.domain.dtos;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long id;

    private String name;
    private String description;
}
