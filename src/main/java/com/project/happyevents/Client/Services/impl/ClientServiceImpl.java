package com.project.happyevents.Client.Services.impl;
import com.project.happyevents.Client.DTO.ClientDTO;
import com.project.happyevents.Client.Entity.Client;
import com.project.happyevents.Client.Mapper.ClientMapper;
import com.project.happyevents.Client.Repository.ClientRepository;
import com.project.happyevents.Client.Services.ClientService;
import com.project.happyevents.EventManager.DTO.EventDTO;
import com.project.happyevents.EventManager.Entity.Event;
import com.project.happyevents.EventManager.Entity.EventHost;
import com.project.happyevents.EventManager.Repository.EventHostRepository;
import com.project.happyevents.EventManager.Repository.EventRepository;
import com.project.happyevents.EventManager.Services.emailService;
import com.project.happyevents.EventManager.Services.eventService;
import com.project.happyevents.common.utils.HashMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service

public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventHostRepository eventHostRepository;
    @Autowired
    private emailService emailService;
    @Autowired
    private eventService eventService;
    @Autowired
    private HashMapUtil hashMapUtil;

    @Override
    public String AddClient(ClientDTO clientDTO) {
        Client client = this.clientMapper.toEntity(clientDTO);

        boolean clientExists = this.clientRepository.existsByEmail(client.getEmail());
        boolean eventHostExists = this.eventHostRepository.existsByEmail(client.getEmail());

        if (clientExists || eventHostExists) {
            return "Email already exists as a Client or as an Event Host. Please try with different email.";
        }
        Map<String, Object> context = new HashMap<>();
        context.put("Host_Name", client.getName());
        try {
            String a = emailService.sendMailWithTemplate(client.getEmail(), "clientTemplate/ClientAdded", context);
            if (a=="ok") {
                this.clientRepository.saveAndFlush(client);
                return "Client Added Successfully";
            }
            else{
                return "Mailing Failed. Incorrect Email";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return("Failed ");
        }

    }

    @Override
    public Client GetClientByID(Long clientid) {
        return this.clientRepository.findById(clientid).orElseThrow(()-> new RuntimeException("Client Unavailable."));
    }


    @Override
    public String registerToEvent(String Email, EventDTO eventDTO) {
        Client client = this.clientRepository.findByEmail(Email);
        if (client!=null){
        Event event = this.eventRepository.findById(eventDTO.getId()).orElseThrow(()-> new RuntimeException("Event Unavailable"));

        if (client.getEvents().contains(event) || event.getParticipants().contains(client)){
            return "User is already registered as a participant.";
        }

        EventHost dto = event.getHost();
        Long part =this.eventService.getTotalRegisteredUsers(event.getId())+1;
        Map<String, Object> context = hashMapUtil.toRegister(event,dto,part,client);

        try {
            String a = emailService.sendMailWithTemplate(client.getEmail(), "clientTemplate/RegisteredEvent", context);
            String b = emailService.sendMailWithTemplate(dto.getEmail(), "eventHostTemplate/userRegistered",context);
            if ((a=="ok") && (b=="ok") ){

                client.getEvents().add(event);
                event.getParticipants().add(client);
                this.clientRepository.saveAndFlush(client);
                this.eventRepository.saveAndFlush(event);
                return "Client Added Successfully";
            }
            else{
                return "Mailing Failed. Incorrect Email";}}
        catch (Exception e) {
            e.printStackTrace();
            return("Failed ");}

    }
    else{
    throw new RuntimeException("Client Not Found");
    }}

    @Override
    public String deregisterFromEvent(String email, EventDTO eventDTO) {
        Client client = this.clientRepository.findByEmail(email);
        Event event = this.eventRepository.findById(eventDTO.getId()).orElseThrow(()-> new RuntimeException("Event Unavailable"));

        if (client.getEvents().contains(event) && event.getParticipants().contains(client)){

            EventHost dto = event.getHost();
            Long part =this.eventService.getTotalRegisteredUsers(event.getId())-1;
            Map<String, Object>  context=hashMapUtil.toRegister(event,dto,part,client);

            try {
                String a = emailService.sendMailWithTemplate(client.getEmail(), "clientTemplate/DeregisteredEvent", context);
                String b = emailService.sendMailWithTemplate(dto.getEmail(), "eventHostTemplate/userDeregistered",context);
                if ((a=="ok") && (b=="ok") ){
                    client.getEvents().remove(event);
                    event.getParticipants().remove(client);
                    clientRepository.flush();
                    eventRepository.flush();
                    return "Client De-registered Successfully";}
                else{
                    return "Mailing Failed. Incorrect Email";}}
                catch (Exception e) {
                    e.printStackTrace();
                    return("Failed ");}}
    return "ok";
    }



}
