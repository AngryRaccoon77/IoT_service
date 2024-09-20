package com.example.iotservice.repositories;

import com.example.iotservice.models.DeviceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface DeviceServiceRepository extends JpaRepository<DeviceService, UUID> {
    DeviceService findByName(String name);
}
