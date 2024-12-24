package com.example.iotservice.models.enums;

public enum ServiceType {
    WATERLEAK, SMOKE, GAS, MOTION, TEMPERATURE, HUMIDITY, DOOR, WINDOW, LIGHT, SWITCH, TV;

    @Override
    public String toString() {
        return name();
    }
}
