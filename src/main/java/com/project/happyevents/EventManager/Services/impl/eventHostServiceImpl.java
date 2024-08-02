package com.project.happyevents.EventManager.Services.impl;

import com.project.happyevents.Client.Repository.ClientRepository;
import com.project.happyevents.EventManager.DTO.EventHostDTO;
import com.project.happyevents.EventManager.Entity.EventHost;
import com.project.happyevents.EventManager.Mapper.EventHostMapper;
import com.project.happyevents.EventManager.Repository.EventHostRepository;
import com.project.happyevents.EventManager.Services.emailService;
import com.project.happyevents.EventManager.Services.eventHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class eventHostServiceImpl implements eventHostService {
    @Autowired
    EventHostRepository hostrep;
    @Autowired
    EventHostMapper mapper;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    emailService emailService;

    @Override
    public String AddHostRepo(EventHostDTO dto) {
        EventHost host = this.mapper.toEntity(dto);
        boolean clientExists = this.clientRepository.existsByEmail(host.getEmail());
        boolean eventHostExists = this.hostrep.existsByEmail(host.getEmail());

        if (clientExists || eventHostExists) {
            return "Email already exists as a Client or as an Event Host. Please try with different email.";
        }
        Map<String, Object> context = new HashMap<>();
        context.put("Host_Name", host.getName());
        try {
            String a = emailService.sendMailWithTemplate(host.getEmail(), "eventHostTemplate/hostAdded", context);
            if (a=="ok") {
                this.hostrep.saveAndFlush(host);
                return "host registered.";
            }
            else{
                return "Mailing Failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return("Failed ");
        }
    }

    @Override
    public EventHost getbyid(Long ID) {
        EventHost host = this.hostrep.findById(ID).orElseThrow(() -> new RuntimeException("Host is invalid"));
        return host;
    }

}
