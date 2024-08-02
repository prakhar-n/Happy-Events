package com.project.happyevents.EventManager.Services;

import com.project.happyevents.EventManager.DTO.ClientInfoDTO;
import com.project.happyevents.EventManager.DTO.EventDTO;

import java.util.List;

public interface eventService {
    EventDTO FindById(Long eventID);
    String AddEvent(EventDTO eventDTO, Long hostID);
    String UpdateEvent(EventDTO eventDTO, Long EventID);
    String DeleteEvent(Long EventID);
    EventDTO SingleEventDetails(Long EventID);
    List<EventDTO>GetAllEvents();
    List<EventDTO> getEventsByHost(Long hostID);
    List<ClientInfoDTO> getRegisteredClientsForEvent(Long eventID);
    Boolean HostEventCheck(Long Hostid, Long EventId);
    Long getTotalRegisteredUsers(Long eventId);

}
