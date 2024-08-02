package com.project.happyevents.EventManager.Repository;

import com.project.happyevents.EventManager.Entity.EventHost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventHostRepository extends JpaRepository<EventHost, Long> {
    EventHost findByEmail(String Email);
    boolean existsByEmail(String email);

}
