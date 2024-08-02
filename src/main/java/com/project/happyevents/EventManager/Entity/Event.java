package com.project.happyevents.EventManager.Entity;

import com.project.happyevents.Client.Entity.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String location;
    private String date;
    private String time;
    private String pamphletUrl; // URL to the uploaded pamphlet



    @ManyToOne
    @JoinColumn(name = "host_id")
    private EventHost host;

    @ManyToMany
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Client> participants;
}
