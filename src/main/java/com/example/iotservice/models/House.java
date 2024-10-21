package com.example.iotservice.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "houses")
public class House extends BaseEntity{
    private String address;
    private User user;
    private Set<Hub> hubs;
    public House(){}
    public House(String address){
        this.address = address;
    }

    @Column(name = "address")
    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL)
    public Set<Hub> getHubs(){
        return hubs;
    }

    public void setHubs(Set<Hub> hubs){
        this.hubs = hubs;
    }
}
