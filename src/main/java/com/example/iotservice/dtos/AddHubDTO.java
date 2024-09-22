package com.example.iotservice.dtos;

import com.example.iotservice.models.House;

import java.util.UUID;

public class AddHubDTO {
    private String name;
    private String type;

    private House house;

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

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}