package com.project.happyevents.EventManager.Repository;
import com.project.happyevents.EventManager.Entity.Event;
import com.project.happyevents.Client.Entity.Client;
import com.project.happyevents.EventManager.Entity.EventHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByParticipants(Client client);
    List<Event>findByHost(EventHost host);
    Event findByidAndHost_id(Long event_id, Long host_id);

}