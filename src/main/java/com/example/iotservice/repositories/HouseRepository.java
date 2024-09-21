package com.example.iotservice.repositories;

import com.example.iotservice.models.House;
import com.example.iotservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface HouseRepository extends JpaRepository<House, UUID> {
    House findByName(String name);
    List<House> findByUserId(UUID userId);

}
