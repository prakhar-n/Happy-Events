package com.project.happyevents.EventManager.Services;

import com.project.happyevents.EventManager.DTO.EventHostDTO;
import com.project.happyevents.EventManager.Entity.EventHost;

public interface eventHostService {
    String AddHostRepo(EventHostDTO dto);
    EventHost getbyid(Long ID);
}
