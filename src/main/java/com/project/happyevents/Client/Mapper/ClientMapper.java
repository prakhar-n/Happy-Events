package com.project.happyevents.Client.Mapper;

import com.project.happyevents.Client.DTO.ClientDTO;
import com.project.happyevents.Client.Entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientDTO toDto(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setRole(client.getRole());
        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setPassword(dto.getPassword());
        client.setEmail(dto.getEmail());
        client.setRole(dto.getRole());
        return client;
    }
}
