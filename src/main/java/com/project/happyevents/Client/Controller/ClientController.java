package com.project.happyevents.Client.Controller;
import com.project.happyevents.Client.Services.ClientService;
import com.project.happyevents.EventManager.DTO.EventDTO;
import com.project.happyevents.EventManager.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.happyevents.EventManager.Services.eventService;


import java.util.List;

@RestController
@RequestMapping("/user")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private eventService eventService;
    @Autowired
    private EventRepository eventRepository;


    @GetMapping("/events")
    public List<EventDTO> AllEvents(){          //All events
        return this.eventService.GetAllEvents();
    }

    @GetMapping("/events/{eventId}")
    public EventDTO showEventDetails(@PathVariable Long eventId, Model model) {
        EventDTO event= this.eventService.FindById(eventId);    // specific events
        if (event == null) {
            // Handle event not found
            return null;
        }
        model.addAttribute("event", event);
        return event;// HTML template to display event details
    }

    @PostMapping("/events/{eventId}/register")
    public String registerForEvent(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        EventDTO event= this.eventService.FindById(eventId);    //register
        if (event == null) {
            // Handle event not found
            return "error"; // or redirect to an error page
        }

        String a = clientService.registerToEvent(email, event);
        return a;
//        return "redirect:/events/" + eventId; // Redirect back to event details page
    }

    @PostMapping("/events/{eventId}/deregister")
    public String deregisterFromEvent(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        EventDTO event= this.eventService.FindById(eventId);
        if (event == null) {
            // Handle event not found
            return "error"; // or redirect to an error page
        }
       String a = clientService.deregisterFromEvent(email, event);
        return a;
//        return "redirect:/events/" + eventId; // Redirect back to event details page
    }
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: " + ex.getMessage());
    }
}