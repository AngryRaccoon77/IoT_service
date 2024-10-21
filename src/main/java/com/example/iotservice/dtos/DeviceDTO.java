package com.example.iotservice.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private String name;

    private boolean status;
    @JsonBackReference
    private HubDTO hub;
    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public HubDTO getHub() {
        return hub;
    }

    public void setHub(HubDTO hub) {
        this.hub = hub;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
