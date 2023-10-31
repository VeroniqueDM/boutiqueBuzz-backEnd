package com.api.boutiquebuzz.domain.dtos;


import lombok.Data;

import java.util.List;

@Data
public class CollectionResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private List<String> imageUrls;
    private String designerName;

}