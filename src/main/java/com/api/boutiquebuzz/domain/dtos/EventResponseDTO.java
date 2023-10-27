package com.api.boutiquebuzz.domain.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime eventDate;
}