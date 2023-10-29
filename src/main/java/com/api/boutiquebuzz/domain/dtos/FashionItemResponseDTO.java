package com.api.boutiquebuzz.domain.dtos;

import lombok.Data;

@Data
public class FashionItemResponseDTO {
    private Long id;
    private String name;
    private String description;
    // Designer details
    private String designerName;
    private String designerEmail;
    private String designerPhone;
}