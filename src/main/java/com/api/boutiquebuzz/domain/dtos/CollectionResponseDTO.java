package com.api.boutiquebuzz.domain.dtos;


import lombok.Data;

@Data
public class CollectionResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;

}