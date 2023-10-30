package com.api.boutiquebuzz.web;
import com.api.boutiquebuzz.domain.dtos.NewsResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import com.api.boutiquebuzz.domain.dtos.CreateEventRequestDTO;
import com.api.boutiquebuzz.domain.dtos.EventResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateEventRequestDTO;
import com.api.boutiquebuzz.services.EventService;
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
@RequestMapping("/events")
public class EventController {  

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        List<EventResponseDTO> eventResponseDTOs = eventService.getAllEvents();
        return ResponseEntity.ok(eventResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            EventResponseDTO event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid CreateEventRequestDTO eventDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        EventResponseDTO createdEvent = eventService.createEvent(eventDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }
    @PreAuthorize("@customPermissionEvaluator.hasPermission(authentication, T(FashionEvent).class, 'WRITE')")

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody @Valid UpdateEventRequestDTO eventDTO, BindingResult bindingResult) {
        ResponseEntity<?> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;
        try {
            EventResponseDTO updatedEvent = eventService.updateEvent(id, eventDTO);
            return ResponseEntity.ok(updatedEvent);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("@customPermissionEvaluator.hasPermission(authentication, T(FashionEvent).class, 'WRITE')")

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        try {
            EventResponseDTO deletedEvent = eventService.deleteEvent(id);
            return ResponseEntity.ok(deletedEvent);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<EventResponseDTO>> searchNews(@RequestParam(name = "keyword") String keyword) {
        List<EventResponseDTO> searchResults = eventService.searchEvents(keyword);
        return ResponseEntity.ok(searchResults);
    }
}
