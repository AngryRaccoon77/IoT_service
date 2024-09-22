package com.example.iotservice.dtos;

import java.util.UUID;

public class AddDeviceServiceDTO {

    private String name;
    private String type;

    private DeviceDTO device;
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

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }
}
