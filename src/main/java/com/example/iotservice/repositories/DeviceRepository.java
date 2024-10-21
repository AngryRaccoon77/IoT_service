package com.example.iotservice.repositories;

import com.example.iotservice.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    Device findByName(String name);
    List<Device> findByHubId(UUID controllerId);

}
