package com.example.iotservice.services;

import com.example.iotservice.dtos.AddHubDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.dtos.DeviceDTO;

import java.util.List;
import java.util.UUID;

public interface HubService {
    HubDTO getHubById(UUID id);
    List<HubDTO> getAllHubs();
    HubDTO createHub(AddHubDTO HubDTO);
    HubDTO updateHub(UUID id, HubDTO HubDTO);
    void deleteHub(UUID id);
    List<DeviceDTO> getDevicesByHubId(UUID HubId);

}