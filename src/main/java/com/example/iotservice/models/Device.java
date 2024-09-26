package com.example.iotservice.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "divices")
public class Device extends BaseEntity{
    private boolean status;

    private Hub hub;

    private String room;

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
        return hub;
    }

    public void setController(Hub controller){
        this.hub = controller;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL)
    public Set<DeviceService> getServices(){
        return services;
    }

    public void setServices(Set<DeviceService> services){
        this.services = services;
    }

    @Column(name = "room")
    public String getRoom(){
        return room;
    }

    public void setRoom(String room){
        this.room = room;
    }
}
