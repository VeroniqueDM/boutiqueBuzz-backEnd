package com.api.boutiquebuzz.services;
import com.api.boutiquebuzz.domain.entities.UserEntity;
import com.api.boutiquebuzz.repositories.UserEntityRepository;
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
    private final UserEntityRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public NewsServiceImpl(NewsRepository fashionNewsRepository, UserEntityRepository userRepository, ModelMapper modelMapper) {
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
    // Retrieve the authenticated user (you need to have user authentication in place)
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // You should have a UserDetails implementation that includes your user details
    if (authentication != null) {
        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();

        // Use the user details to set the owner of the news article
        UserEntity owner = userRepository.findByEmail(userDetails.getAttribute("email")).orElse(null);

        if (owner == null) {
            // Handle the case when the owner doesn't exist or throw an exception
            return null;
        }

        NewsArticle news = modelMapper.map(newsDTO, NewsArticle.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        news.setPublishedAt(currentDateTime);
        news.setUpdatedAt(currentDateTime);

        // Set the owner of the news article
        news.setOwner(owner);

        NewsArticle savedNews = fashionNewsRepository.save(news);
        return modelMapper.map(savedNews, NewsResponseDTO.class);
    }

    return null; // Handle the case when there's no authenticated user
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
}