package com.example.iotservice.dtos;

import com.example.iotservice.models.House;
import com.example.iotservice.models.enums.ControllerType;

import java.util.UUID;

public class AddHubDTO {
    private String name;
    private ControllerType type;
    private Boolean status;

    private HouseDTO house;

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

    public HouseDTO getHouse() {
        return house;
    }

    public void setHouse(HouseDTO house) {
        this.house = house;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }
}