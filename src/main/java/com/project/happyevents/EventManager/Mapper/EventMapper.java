package com.project.happyevents.EventManager.Mapper;

import com.project.happyevents.EventManager.DTO.EventDTO;
import com.project.happyevents.EventManager.Entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventDTO toDto(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setDate(event.getDate());
        dto.setTime(event.getTime());
        dto.setPamphletUrl(event.getPamphletUrl());
        dto.setHostID(event.getHost().getId());
        return dto;
    }

    public Event toEntity(EventDTO dto) {
        Event event = new Event();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDate(dto.getDate());
        event.setTime(dto.getTime());
        event.setPamphletUrl(dto.getPamphletUrl());
        return event;
    }
}