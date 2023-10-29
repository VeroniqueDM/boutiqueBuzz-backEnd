package com.api.boutiquebuzz.services;

import com.api.boutiquebuzz.domain.dtos.CreateEventRequestDTO;
import com.api.boutiquebuzz.domain.dtos.EventResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateEventRequestDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface EventService {
    List<EventResponseDTO> getAllEvents();
    EventResponseDTO getEventById(Long eventId);
    EventResponseDTO createEvent(CreateEventRequestDTO eventDTO);
    EventResponseDTO updateEvent(Long id, UpdateEventRequestDTO updateEventDTO);
    EventResponseDTO deleteEvent(Long eventId);

    boolean isOwner(UserDetails userDetails, Long id);
}
