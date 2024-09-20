package com.example.iotservice.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private String email;
    private Set<House> houses;

    public User(){}
    public User(String email){
        this.email = email;
    }

    @Column(name = "email")
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public Set<House> getHouses(){
        return houses;
    }

    public void setHouses(Set<House> houses){
        this.houses = houses;
    }
}
