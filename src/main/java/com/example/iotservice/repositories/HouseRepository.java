package com.example.iotservice.repositories;

import com.example.iotservice.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface HouseRepository extends JpaRepository<House, UUID> {
    House findByName(String name);
}
