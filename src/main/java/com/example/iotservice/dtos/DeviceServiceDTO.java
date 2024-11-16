package com.example.iotservice.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.UUID;

public class DeviceServiceDTO {
        private UUID id;
        private String name;
        private String type;
        @JsonBackReference
        private DeviceDTO device;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }
}
