package com.example.iotservice.dtos;

import java.util.UUID;

public class UpdateDeviceDTO {
    private UUID id;
    private String name;
    private boolean status;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public boolean getStatus(){
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}