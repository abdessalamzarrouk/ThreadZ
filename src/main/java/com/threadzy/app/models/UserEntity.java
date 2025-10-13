package com.threadzy.app.models;
import java.security.Timestamp;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.threadzy.app.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String username;
    private String email; 
    private boolean enabled;
    private String passwordhash;
    private boolean locked;
    private Timestamp createdAt;
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }
    @Override
    public String getPassword() {
        return passwordhash;
    }
}