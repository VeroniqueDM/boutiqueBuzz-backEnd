package com.api.boutiquebuzz.domain.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

//    @NotBlank(message = "Date is required")
    private LocalDateTime eventDate;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
}