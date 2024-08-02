package com.project.happyevents.EventManager.Services.impl;

import com.project.happyevents.EventManager.DTO.ClientInfoDTO;
import com.project.happyevents.EventManager.DTO.EventDTO;
import com.project.happyevents.EventManager.Entity.Event;
import com.project.happyevents.EventManager.Entity.EventHost;
import com.project.happyevents.EventManager.Mapper.EventMapper;
import com.project.happyevents.EventManager.Repository.EventHostRepository;
import com.project.happyevents.EventManager.Repository.EventRepository;
import com.project.happyevents.EventManager.Services.eventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class eventServiceImpl implements eventService {
    @Autowired
    private EventMapper mapper;
    @Autowired
    private EventHostRepository hostRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private emailServiceImpl emailService;



    @Override
    public EventDTO FindById(Long eventID) {
        Event event = this.eventRepository.findById(eventID).orElse(null);
        EventDTO eventDTO = this.mapper.toDto(event);
        return eventDTO;
    }

    @Override
    public String AddEvent(EventDTO eventDTO, Long hostID) {
        Event event = this.mapper.toEntity(eventDTO);
        EventHost host = this.hostRepository.findById(hostID).orElseThrow(()-> new RuntimeException("HostUnavail"));
        event.setHost(host);
        Map<String, Object> context = new HashMap<>();
        context.put("Host_Name", host.getName());
        context.put("Event_Title", event.getName());
        try {
            String a = emailService.sendMailWithTemplate(host.getEmail(), "eventHostTemplate/eventAdded", context);
            if (a=="ok") {
                this.eventRepository.saveAndFlush(event);
                return "Event Added";
            }
            else{
                return "Mailing Failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return("Failed ");
        }

        //mailcode

    }

    @Override
    public String UpdateEvent(EventDTO dto, Long EventID) {
        Event event = this.eventRepository.findById(EventID).orElseThrow(()-> new RuntimeException("EventUnavailable"));
        EventHost host = event.getHost();
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDate(dto.getDate());
        event.setTime(dto.getTime());
        event.setPamphletUrl(dto.getPamphletUrl());
        Map<String, Object> context = new HashMap<>();

        context.put("Host_Name", host.getName());
        context.put("Event_Title", event.getName());
        try {
            String a = emailService.sendMailWithTemplate(host.getEmail(), "eventHostTemplate/eventDeleted", context);
            if (a=="ok") {
                this.eventRepository.saveAndFlush(event);
                return "Event Deleted";
            }
            else{
                return "Mailing Failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return("Failed ");
        }
        //mailcode
    }

    @Override
    public String DeleteEvent(Long EventID) {
        Event event = this.eventRepository.findById(EventID).orElseThrow(()-> new RuntimeException("EventUnavailable"));
        EventHost host = event.getHost();
        Map<String, Object> context = new HashMap<>();

        context.put("Host_Name", host.getName());
        context.put("Event_Title", event.getName());
        try {
            String a = emailService.sendMailWithTemplate(host.getEmail(), "eventUpdated", context);
            if (a=="ok") {
                this.eventRepository.delete(event);
                return "Event Deleted";
            }
            else{
                return "Mailing Failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return("Failed ");
        }

        //mailcode
    }

    @Override
    public EventDTO SingleEventDetails(Long EventID) {
        Event event = this.eventRepository.findById(EventID).orElseThrow(()-> new RuntimeException("EventUnavailable"));
        EventDTO dto= this.mapper.toDto(event);
        return dto;
    }

    @Override
    public List<EventDTO> GetAllEvents() {
        List<Event> events = this.eventRepository.findAll();
        return events.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getEventsByHost(Long hostID) {
        EventHost host = this.hostRepository.findById(hostID).orElseThrow(() -> new RuntimeException("HostUnavailable"));
        List<Event> events = this.eventRepository.findByHost(host);
        return events.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientInfoDTO> getRegisteredClientsForEvent(Long eventID) {
        Event event = this.eventRepository.findById(eventID).orElseThrow(() -> new RuntimeException("EventUnavailable"));
        return event.getParticipants().stream()
                .map(client -> new ClientInfoDTO(client.getId(), client.getName(), client.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean HostEventCheck(Long Hostid, Long EventId) {
        Event event = this.eventRepository.findByidAndHost_id(EventId,Hostid);
        if (event!=null){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Long getTotalRegisteredUsers(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
        int a =  event.getParticipants().size();
        return Long.valueOf(a);
    }

}
