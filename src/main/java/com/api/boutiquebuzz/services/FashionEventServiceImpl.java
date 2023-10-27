package com.api.boutiquebuzz.services;

import com.api.boutiquebuzz.domain.dtos.CreateEventRequestDTO;
import com.api.boutiquebuzz.domain.dtos.EventResponseDTO;
import com.api.boutiquebuzz.domain.dtos.UpdateEventRequestDTO;
import com.api.boutiquebuzz.domain.entities.FashionEvent;
import com.api.boutiquebuzz.repositories.EventRepository;
import com.api.boutiquebuzz.utils.ErrorConstants;
import com.api.boutiquebuzz.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FashionEventServiceImpl implements EventService {
    private final EventRepository fashionEventRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FashionEventServiceImpl(EventRepository fashionEventRepository, ModelMapper modelMapper) {
        this.fashionEventRepository = fashionEventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EventResponseDTO> getAllEvents() {
        List<FashionEvent> fashionEventList = fashionEventRepository.findAll();
        return fashionEventList.stream()
                .map(event -> modelMapper.map(event, EventResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDTO getEventById(Long eventId) {
        Optional<FashionEvent> eventOptional = fashionEventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            return modelMapper.map(eventOptional.get(), EventResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.EVENT_NOT_FOUND_MESSAGE, eventId));
        }
    }

    @Override
    public EventResponseDTO createEvent(CreateEventRequestDTO eventDTO) {
        FashionEvent event = modelMapper.map(eventDTO, FashionEvent.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        event.setPublishedAt(currentDateTime);
        event.setUpdatedAt(currentDateTime);
        FashionEvent savedEvent = fashionEventRepository.save(event);
        return modelMapper.map(savedEvent, EventResponseDTO.class);
    }

    @Override
    public EventResponseDTO updateEvent(Long eventId, UpdateEventRequestDTO updateEventDTO) {
        Optional<FashionEvent> eventOptional = fashionEventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            FashionEvent existingEvent = eventOptional.get();

            if (hasUpdates(existingEvent, updateEventDTO)) {
                existingEvent.setUpdatedAt(LocalDateTime.now());
            }

            modelMapper.map(updateEventDTO, existingEvent);
            FashionEvent updatedEvent = fashionEventRepository.save(existingEvent);

            return modelMapper.map(updatedEvent, EventResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.EVENT_NOT_FOUND_MESSAGE, eventId));
        }
    }

    @Override
    public EventResponseDTO deleteEvent(Long eventId) {
        FashionEvent event = fashionEventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.EVENT_NOT_FOUND_MESSAGE, eventId)));

        fashionEventRepository.delete(event);

        return modelMapper.map(event, EventResponseDTO.class);
    }

    private boolean hasUpdates(FashionEvent existingEvent, UpdateEventRequestDTO updateEventDTO) {
        boolean hasUpdates = !existingEvent.getTitle().equals(updateEventDTO.getTitle()) ||
                !existingEvent.getDescription().equals(updateEventDTO.getDescription());

        return hasUpdates;
    }
}