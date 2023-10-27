package com.api.boutiquebuzz.domain.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateNewsRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;
//    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
}