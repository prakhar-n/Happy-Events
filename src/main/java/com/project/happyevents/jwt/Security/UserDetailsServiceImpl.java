package com.project.happyevents.jwt.Security;
import com.project.happyevents.Client.Entity.Client;
import com.project.happyevents.Client.Repository.ClientRepository;
import com.project.happyevents.EventManager.Entity.EventHost;
import com.project.happyevents.EventManager.Repository.EventHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EventHostRepository eventHostRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username);
        if (client != null) {
            return new CustomUserDetails(client);
        }

        EventHost eventHost = eventHostRepository.findByEmail(username);
        if (eventHost != null) {
            return new CustomUserDetails(eventHost);
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }}
