package com.example.iotservice.models;

import jakarta.persistence.*;

@Entity
@Table(name = "houses")
public class House extends BaseEntity{
    private String address;
    private User user;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}
