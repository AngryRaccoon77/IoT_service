package com.example.iotservice.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;
import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private String name;
    private boolean status;
    @JsonBackReference
    private HubDTO hub;
    @JsonIgnore
    private Set<DeviceServiceDTO> services;
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

    public Set<DeviceServiceDTO> getServices() {
        return services;
    }

    public void setServices(Set<DeviceServiceDTO> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", services=" + services +
                '}';
    }
}
