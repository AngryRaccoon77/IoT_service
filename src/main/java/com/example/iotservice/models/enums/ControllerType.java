package com.example.iotservice.models.enums;

public enum ControllerType {
    ZIGBEE, MIIO, BLUETOOTH, HOMEKIT, MATTER, THREAD;

    @Override
    public String toString() {
        return name();
    }
}
