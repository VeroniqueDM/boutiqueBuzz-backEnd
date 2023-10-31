package com.api.boutiquebuzz.web;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import com.api.boutiquebuzz.domain.dtos.CreateNewsRequestDTO;
import com.api.boutiquebuzz.domain.dtos.NewsResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateNewsRequestDTO;
import com.api.boutiquebuzz.services.NewsService;
import com.api.boutiquebuzz.utils.ErrorUtil;
import com.api.boutiquebuzz.utils.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsResponseDTO>> getAllNews() {
        List<NewsResponseDTO> newsResponseDTOs = newsService.getAllNews();
        return ResponseEntity.ok(newsResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        try {
            NewsResponseDTO news = newsService.getNewsById(id);
            return ResponseEntity.ok(news);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createNews(@RequestBody @Valid CreateNewsRequestDTO newsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        NewsResponseDTO createdNews = newsService.createNews(newsDTO);
        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }
//    @PreAuthorize("@customPermissionEvaluator.hasPermission(authentication, T(com.api.boutiquebuzz.domain.entities.NewsArticle).class, 'WRITE')")
// TODO: fix this

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @RequestBody @Valid UpdateNewsRequestDTO newsDTO, BindingResult bindingResult) {
        ResponseEntity<?> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;
        try {
            NewsResponseDTO updatedNews = newsService.updateNews(id, newsDTO);
            return ResponseEntity.ok(updatedNews);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("@customPermissionEvaluator.hasPermission(authentication, #id, 'NewsArticle', 'DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        try {
            NewsResponseDTO deletedNews = newsService.deleteNews(id);
            return ResponseEntity.ok(deletedNews);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsResponseDTO>> searchNews(@RequestParam(name = "keyword") String keyword) {
        List<NewsResponseDTO> searchResults = newsService.searchNews(keyword);
        return ResponseEntity.ok(searchResults);
    }
}