package com.project.happyevents.EventManager.Controller;

import com.project.happyevents.EventManager.DTO.ClientInfoDTO;
import com.project.happyevents.EventManager.DTO.EventDTO;
import com.project.happyevents.EventManager.DTO.EventHostDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Host")
public class EventController {
    @Autowired
    com.project.happyevents.EventManager.Services.eventService eventService;
    @Autowired
    com.project.happyevents.EventManager.Services.eventHostService eventHostService;

    @PostMapping("/Signup")
    public String SignUpHost(@Valid @RequestBody EventHostDTO dto){
        String signup = this.eventHostService.AddHostRepo(dto);
        return signup;
        //tick
    }

    @PostMapping("{hostid}/AddEvent")
    public String AddEvent(@Valid @RequestBody EventDTO eventDTO, @PathVariable Long hostid) {
        eventService.AddEvent(eventDTO, hostid);
        return "Event Added";
        //tick
    }

    @PutMapping("{hostid}/{eventid}/UpdateEvent")
    public String UpdateEvent(@RequestBody EventDTO eventDTO,@PathVariable Long hostid, @PathVariable Long eventid) {
        Boolean a = eventService.HostEventCheck(hostid,eventid);
        if (a==true){
        eventService.UpdateEvent(eventDTO, eventid);
        return "Event Updated";}
        else{
            return "Failed Unknown User/Unknown Event";
        }//tick
    }

    @DeleteMapping("{hostid}/{Eventid}/DeleteEvent")
    public String UpdateEvent(@PathVariable Long hostid,@PathVariable Long Eventid) {
        Boolean a = eventService.HostEventCheck(hostid,Eventid);
        if (a==true){
        eventService.DeleteEvent(Eventid);
        return "Event Deleted";}
        else{
            return "failed";
        }//tick
    }

    @GetMapping("{hostid}/AllEvents")
    public List<EventDTO> getEventsByHost(@PathVariable Long hostid) {

        return eventService.getEventsByHost(hostid);
        //tick
    }

    @GetMapping("/{EventId}")
    public EventDTO getSingleEvent(@PathVariable Long EventId){
        return eventService.SingleEventDetails(EventId);
        //tick
    }

    @GetMapping("{hostid}/{eventid}/participants")
    public List<ClientInfoDTO> getRegisteredClientsForEvent(@PathVariable Long eventid) {
        return eventService.getRegisteredClientsForEvent(eventid);
        //tick
    }

}