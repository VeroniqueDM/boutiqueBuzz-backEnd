package com.api.boutiquebuzz.domain.dtos;

import lombok.Data;
import java.util.List;

@Data
public class AllCollectionsResponseDTO {
    private List<CollectionResponseDTO> collections;
}