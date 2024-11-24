package com.example.iotservice.dtos;

import com.example.iotservice.models.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.UUID;

public class DeviceServiceDTO {
        private UUID id;
        private String name;
        private ServiceType type;

        @JsonBackReference
        private DeviceDTO device;

        private String data;

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

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DeviceService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", data='" + data + '\'' +
                '}';
    }
}
