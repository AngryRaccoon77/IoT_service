package com.example.iotservice.models;


import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {
    protected UUID id;
    protected String name;

    protected Date created;
    protected Date modified;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public UUID getId(){
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "created")
    public Date getCreated(){
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "modified")
    public Date getModified(){
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
