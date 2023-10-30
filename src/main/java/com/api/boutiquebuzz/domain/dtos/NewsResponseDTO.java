package com.api.boutiquebuzz.domain.dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime publishedAt;
    private Long ownerId;

}