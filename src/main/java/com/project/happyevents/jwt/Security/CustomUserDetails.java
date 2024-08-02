package com.project.happyevents.jwt.Security;
import com.project.happyevents.Client.Entity.Client;
import com.project.happyevents.EventManager.Entity.EventHost;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String role;

    public CustomUserDetails(Client client) {
        this.username = client.getEmail();
        this.password = client.getPassword();
        this.role = "ROLE_CLIENT";
    }

    public CustomUserDetails(EventHost eventHost) {
        this.username = eventHost.getEmail();
        this.password = eventHost.getPassword();
        this.role = "HOST";
    }
    public CustomUserDetails(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
