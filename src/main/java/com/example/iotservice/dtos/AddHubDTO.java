package com.example.iotservice.dtos;

import com.example.iotservice.models.House;
import com.example.iotservice.models.enums.ControllerType;

import java.util.UUID;

public class AddHubDTO {
    private String name;
    private ControllerType type;
    private String status;

    private House house;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ControllerType getType() {
        return type;
    }

    public void setType(ControllerType type) {
        this.type = type;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setStatus(String online) {
    }
}