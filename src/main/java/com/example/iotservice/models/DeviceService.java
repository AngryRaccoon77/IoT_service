package com.example.iotservice.models;

import com.example.iotservice.models.enums.ServiceType;
import jakarta.persistence.*;


@Entity
@Table(name = "deviceServices")
public class DeviceService extends BaseEntity {
    private ServiceType type;
    private String data;

    private Device device;

    public DeviceService() {
    }

    public DeviceService(ServiceType type, String data) {
        this.type = type;
        this.data = data;
    }

    @Column(name = "type")
    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    @Column(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

}
