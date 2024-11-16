package com.example.iotservice.services;

import com.example.iotservice.dtos.AddDeviceDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.dtos.DeviceServiceDTO;
import com.example.iotservice.dtos.UpdateDeviceDTO;

import java.util.List;
import java.util.UUID;

public interface DeviceService {
    DeviceDTO getDeviceById(UUID id);
    List<DeviceDTO> getAllDevices();
    DeviceDTO createDevice(AddDeviceDTO AdddeviceDTO);
    DeviceDTO updateDevice(UUID id, UpdateDeviceDTO updateDeviceDTO);
    void deleteDevice(UUID id);
    DeviceDTO getDeviceByName(String name);
}