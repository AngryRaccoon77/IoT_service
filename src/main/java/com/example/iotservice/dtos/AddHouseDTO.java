package com.example.iotservice.dtos;

import com.example.iotservice.models.Hub;
import com.example.iotservice.models.User;

import javax.lang.model.element.Name;
import java.util.Set;
import java.util.UUID;

public class AddHouseDTO {
    private String name;
    private String address;

    private UserDTO user;

    private Set<Hub> hubs;


    // Getters and Setters

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Set<Hub> getHubs() {
        return hubs;
    }

    public void setHubs(Set<Hub> hubs) {
        this.hubs = hubs;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}