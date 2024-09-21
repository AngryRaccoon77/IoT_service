package com.example.iotservice.services;

import com.example.iotservice.dtos.AddHouseDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseService {
    HouseDTO getHouseById(UUID id);
    List<HouseDTO> getAllHouses();
    HouseDTO createHouse(AddHouseDTO houseDTO);
    HouseDTO updateHouse(UUID id, HouseDTO houseDTO);
    void deleteHouse(UUID id);
    List<HouseDTO> getHousesByUserId(UUID userId);
}
