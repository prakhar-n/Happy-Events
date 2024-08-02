package com.project.happyevents.jwt.controller;
import com.project.happyevents.Client.Entity.Client;
import com.project.happyevents.Client.Repository.ClientRepository;
import com.project.happyevents.EventManager.Entity.EventHost;
import com.project.happyevents.EventManager.Repository.EventHostRepository;
import com.project.happyevents.jwt.Security.JwtUtil;
import com.project.happyevents.jwt.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EventHostRepository eventHostRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup/client")
    public String registerClient(@RequestBody Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRole("ROLE_CLIENT");
        clientRepository.save(client);
        return "Client registered successfully!";
    }

    @PostMapping("/signup/eventhost")
    public String registerEventHost(@RequestBody EventHost eventHost) {
        eventHost.setPassword(passwordEncoder.encode(eventHost.getPassword()));
        eventHost.setRole("HOST");
        eventHostRepository.save(eventHost);
        return "EventHost registered successfully!";
    }

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String role = userDetails.getAuthorities().iterator().next().getAuthority();
            final String jwt = jwtUtil.generateToken(userDetails.getUsername(), role);
            return "Token is "+ jwt+"/n"+"Role "+role;

        } catch (AuthenticationException e) {
            throw new Exception("Incorrect username or password", e);
        }
    }
}
