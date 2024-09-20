package com.example.iotservice.dtos;

import java.util.UUID;

public class AddUserDTO {

    private String name;
    private String email;

    // Getters and Setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}