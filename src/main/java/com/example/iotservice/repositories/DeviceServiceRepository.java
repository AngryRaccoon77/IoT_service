package com.example.iotservice.repositories;

import com.example.iotservice.models.DeviceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository

public interface DeviceServiceRepository extends JpaRepository<DeviceService, UUID> {
   List<DeviceService> findByDeviceId(UUID deviceId);
    DeviceService findByName(String name);
}
