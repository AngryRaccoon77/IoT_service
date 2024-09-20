package com.example.iotservice.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "divices")
public class Device extends BaseEntity{
    private boolean status;

    private Hub controller;

    private Set<DeviceService> services;

    public Device(){}

    public Device(boolean status){
        this.status = status;
    }


    @Column(name = "status")
    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
    @ManyToOne(optional = false)
    @JoinColumn(name = "controller_id", referencedColumnName = "id", nullable = false)
    public Hub getController(){
        return controller;
    }

    public void setController(Hub controller){
        this.controller = controller;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL)
    public Set<DeviceService> getServices(){
        return services;
    }

    public void setServices(Set<DeviceService> services){
        this.services = services;
    }

}
