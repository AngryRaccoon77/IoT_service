package com.example.iotservice.services;

import com.example.iotservice.dtos.AddDeviceServiceDTO;
import com.example.iotservice.dtos.DeviceServiceDTO;

import java.util.List;
import java.util.UUID;

public interface DeviceServiceService {
    DeviceServiceDTO getDeviceServiceById(UUID id);
    List<DeviceServiceDTO> getAllDeviceServices();
    DeviceServiceDTO createDeviceService(AddDeviceServiceDTO serviceDTO);
    DeviceServiceDTO updateDeviceService(UUID id, DeviceServiceDTO serviceDTO);
    void deleteDeviceService(UUID id);
    List<DeviceServiceDTO> getDeviceServicesByDeviceId(UUID id);
}