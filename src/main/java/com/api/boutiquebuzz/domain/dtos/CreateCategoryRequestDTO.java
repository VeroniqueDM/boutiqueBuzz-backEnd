package com.api.boutiquebuzz.domain.dtos;

import lombok.Data;

@Data

public class CreateCategoryRequestDTO {
    private String name;
    private String description;

}