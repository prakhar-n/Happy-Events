package com.project.happyevents.common.utils;

import com.project.happyevents.Client.Entity.Client;
import com.project.happyevents.EventManager.Entity.Event;
import com.project.happyevents.EventManager.Entity.EventHost;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class HashMapUtil {

    public Map<String, Object> toRegister(Event event, EventHost host, Long Part, Client client){

        Map<String, Object> context = new HashMap<>();
        EventHost dto = event.getHost();
        context.put("Host_Name", client.getName());
        context.put("Event_Name", event.getName());
        context.put("Client_Name", client.getName());
        context.put("Event_Host", dto.getName());
        context.put("Event_Location",event.getLocation());
        context.put("Event_Date", event.getDate());
        context.put("Event_Participants", Part.toString());
        return context;
    }
}
