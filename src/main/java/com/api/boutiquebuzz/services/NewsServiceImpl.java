package com.api.boutiquebuzz.services;

import com.api.boutiquebuzz.user.User;
import com.api.boutiquebuzz.user.UserRepository;
import com.api.boutiquebuzz.utils.ErrorConstants;

import com.api.boutiquebuzz.domain.dtos.CreateNewsRequestDTO;
import com.api.boutiquebuzz.domain.dtos.NewsResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateNewsRequestDTO;
import com.api.boutiquebuzz.domain.entities.NewsArticle;
import com.api.boutiquebuzz.repositories.NewsRepository;
import com.api.boutiquebuzz.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository fashionNewsRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public NewsServiceImpl(NewsRepository fashionNewsRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.fashionNewsRepository = fashionNewsRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NewsResponseDTO> getAllNews() {
        List<NewsArticle> fashionNewsList = fashionNewsRepository.findAll();
        return fashionNewsList.stream()
                .map(news -> modelMapper.map(news, NewsResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public NewsResponseDTO getNewsById(Long newsId) {
        Optional<NewsArticle> newsOptional = fashionNewsRepository.findById(newsId);
        if (newsOptional.isPresent()) {
            return modelMapper.map(newsOptional.get(), NewsResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.NEWS_NOT_FOUND_MESSAGE, newsId));
        }
    }

//    @Override
//    public NewsResponseDTO createNews(CreateNewsRequestDTO newsDTO) {
//        NewsArticle news = modelMapper.map(newsDTO, NewsArticle.class);
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        news.setPublishedAt(currentDateTime);
//        news.setUpdatedAt(currentDateTime);
//        NewsArticle savedNews = fashionNewsRepository.save(news);
//        return modelMapper.map(savedNews, NewsResponseDTO.class);
//    }
@Override
public NewsResponseDTO createNews(CreateNewsRequestDTO newsDTO) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {
        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();

        User owner = userRepository.findByEmail(userDetails.getAttribute("email")).orElse(null);

        if (owner == null) {
            return null;
        }

        NewsArticle news = modelMapper.map(newsDTO, NewsArticle.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        news.setPublishedAt(currentDateTime);
        news.setUpdatedAt(currentDateTime);

        news.setOwner(owner);

        NewsArticle savedNews = fashionNewsRepository.save(news);
        return modelMapper.map(savedNews, NewsResponseDTO.class);
    }

    return null;
}
    @Override
    public NewsResponseDTO updateNews(Long newsId, UpdateNewsRequestDTO updateNewsDTO) {
        Optional<NewsArticle> newsOptional = fashionNewsRepository.findById(newsId);
        if (newsOptional.isPresent()) {
            NewsArticle existingNews = newsOptional.get();

            if (hasUpdates(existingNews, updateNewsDTO)) {
                existingNews.setUpdatedAt(LocalDateTime.now());
            }

            modelMapper.map(updateNewsDTO, existingNews);
            NewsArticle updatedNews = fashionNewsRepository.save(existingNews);

            return modelMapper.map(updatedNews, NewsResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.NEWS_NOT_FOUND_MESSAGE, newsId));
        }
    }

    @Override
    public NewsResponseDTO deleteNews(Long newsId) {
        NewsArticle news = fashionNewsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.NEWS_NOT_FOUND_MESSAGE, newsId)));

        fashionNewsRepository.delete(news);

        return modelMapper.map(news, NewsResponseDTO.class);
    }

    private boolean hasUpdates(NewsArticle existingNews, UpdateNewsRequestDTO updateNewsDTO) {
        boolean hasUpdates = !existingNews.getTitle().equals(updateNewsDTO.getTitle()) ||
                !existingNews.getContent().equals(updateNewsDTO.getContent());

        return hasUpdates;
    }

    @Override
    public List<NewsResponseDTO> searchNews(String keyword) {
        List<NewsArticle> results = fashionNewsRepository.searchNews(keyword);

        List<NewsResponseDTO> newsDTOs = results.stream()
                .map(news -> modelMapper.map(news, NewsResponseDTO.class))
                .collect(Collectors.toList());

        return newsDTOs;
    }
}