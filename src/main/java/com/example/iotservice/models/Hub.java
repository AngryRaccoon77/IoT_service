package com.example.iotservice.models;

import com.example.iotservice.models.enums.ControllerType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "controllers")
public class Hub extends BaseEntity {
    private ControllerType type;
    private String status;
    private House house;

    private Set<Device> devices;

    public Hub(){}

    public Hub(ControllerType type, String status){
        this.type = type;
        this.status = status;
    }

    @Column(name = "type")
    public ControllerType getType(){
        return type;
    }

    public void setType(ControllerType type){
        this.type = type;
    }

    @Column(name = "status")
    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false)
    public House getHouse(){
        return house;
    }

    public void setHouse(House house){
        this.house = house;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "controller", cascade = CascadeType.ALL)
    public Set<Device> getDevices(){
        return devices;
    }

    public void setDevices(Set<Device> devices){
        this.devices = devices;
    }
}
