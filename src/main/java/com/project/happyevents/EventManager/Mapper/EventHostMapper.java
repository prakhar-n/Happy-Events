package com.project.happyevents.EventManager.Mapper;

import com.project.happyevents.EventManager.DTO.EventHostDTO;
import com.project.happyevents.EventManager.Entity.EventHost;
import org.springframework.stereotype.Component;

@Component
public class EventHostMapper {
    public EventHostDTO toDto(EventHost event) {
        EventHostDTO dto = new EventHostDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setEmail(event.getEmail());
        dto.setPassword(event.getPassword());
        dto.setRole(event.getRole());
        return dto;
    }

    public EventHost toEntity(EventHostDTO dto) {
        EventHost event = new EventHost();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setEmail(dto.getEmail());
        event.setPassword(dto.getPassword());
        event.setRole(dto.getRole());
        return event;
    }
}
