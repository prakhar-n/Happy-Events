package com.project.happyevents.Client.Services;

import com.project.happyevents.Client.DTO.ClientDTO;
import com.project.happyevents.Client.Entity.Client;
import com.project.happyevents.EventManager.DTO.EventDTO;

public interface ClientService {
    String AddClient(ClientDTO clientDTO);
    Client GetClientByID(Long clientid);
    String registerToEvent(String email, EventDTO event);
    String deregisterFromEvent(String email, EventDTO event);
}
