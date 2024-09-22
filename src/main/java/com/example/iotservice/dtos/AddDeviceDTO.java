package com.example.iotservice.dtos;

import java.util.UUID;

public class AddDeviceDTO {
    private String name;
    private String type;

    private HubDTO hub;
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

    public HubDTO getHub() {
        return hub;
    }

    public void setHub(HubDTO hub) {
        this.hub = hub;
    }
}
