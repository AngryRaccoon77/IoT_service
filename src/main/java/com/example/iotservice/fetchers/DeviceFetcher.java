package com.example.iotservice.fetchers;

import com.example.iotservice.dtos.AddDeviceDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.services.DeviceService;
import com.example.iotservice.services.HouseService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class DeviceFetcher {

    private DeviceService deviceService;

    @Autowired
    public void setHouseService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    @DgsQuery
    public List<DeviceDTO> devices() {
        return deviceService.getAllDevices();
    }

    @DgsQuery
    public Optional<DeviceDTO> device(UUID id) {
        return Optional.of(deviceService.getDeviceById(id));
    }

    public DeviceDTO addDevice(String name, Boolean status, HubDTO hub) {
        AddDeviceDTO addDeviceDTO = new AddDeviceDTO();
        addDeviceDTO.setName(name);
        addDeviceDTO.setStatus(status);
        addDeviceDTO.setHub(hub);
        return deviceService.createDevice(addDeviceDTO);
    }

    @DgsMutation
    public Boolean deleteDevice(UUID id) {
        try {
            deviceService.deleteDevice(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}