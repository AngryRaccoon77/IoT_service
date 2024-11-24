package com.example.iotservice.dtos;

import com.example.iotservice.models.enums.ServiceType;

import java.util.UUID;

public class AddDeviceServiceDTO {

    private String name;
    private ServiceType type;

    private DeviceDTO device;

    private String data;
    // Getters and Setters

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
}
