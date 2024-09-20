package com.example.iotservice.models.enums;

public enum ServiceType {
    WATERLEAK, SMOKE, GAS, MOTION, TEMPERATURE, HUMIDITY, DOOR, WINDOW, LIGHT, SWITCH;

    @Override
    public String toString() {
        return name();
    }
}
