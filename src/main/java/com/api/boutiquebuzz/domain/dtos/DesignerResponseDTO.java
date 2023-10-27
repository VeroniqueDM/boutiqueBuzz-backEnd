package com.api.boutiquebuzz.domain.dtos;

import lombok.Data;

@Data
public class DesignerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
}