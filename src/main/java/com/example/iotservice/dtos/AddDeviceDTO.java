package com.example.iotservice.dtos;

import java.util.UUID;

public class AddDeviceDTO {
    private String name;
    private String type;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}