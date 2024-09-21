package com.example.iotservice.dtos;

import com.example.iotservice.models.Hub;
import com.example.iotservice.models.User;

import java.util.Set;
import java.util.UUID;

public class HouseDTO {
    private UUID id;
    private String address;

    private User user;

    private Set<Hub> hubs;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Hub> getHubs() {
        return hubs;
    }

    public void setHubs(Set<Hub> hubs) {
        this.hubs = hubs;
    }
}
