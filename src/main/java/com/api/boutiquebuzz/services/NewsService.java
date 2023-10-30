package com.api.boutiquebuzz.services;


import com.api.boutiquebuzz.domain.dtos.CreateNewsRequestDTO;
import com.api.boutiquebuzz.domain.dtos.NewsResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateNewsRequestDTO;

import java.util.List;

public interface NewsService {
    List<NewsResponseDTO> getAllNews();
    NewsResponseDTO getNewsById(Long newsId);
    NewsResponseDTO createNews(CreateNewsRequestDTO newsDTO);
    NewsResponseDTO updateNews(Long id, UpdateNewsRequestDTO updateNewsDTO);
    NewsResponseDTO deleteNews(Long newsId);

    List<NewsResponseDTO> searchNews(String keyword);
}