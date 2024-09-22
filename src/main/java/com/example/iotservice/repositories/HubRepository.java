package com.example.iotservice.repositories;


import com.example.iotservice.models.Hub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HubRepository extends JpaRepository<Hub, UUID> {
    Hub findByName(String name);

    List<Hub> findByHouseId(UUID houseId);
}
