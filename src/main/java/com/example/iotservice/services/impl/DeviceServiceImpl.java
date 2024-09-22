package com.example.iotservice.services.impl;

import com.example.iotservice.dtos.AddDeviceDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.dtos.DeviceServiceDTO;
import com.example.iotservice.models.Device;
import com.example.iotservice.repositories.DeviceRepository;
import com.example.iotservice.services.DeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setDeviceRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DeviceDTO getDeviceById(UUID id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Device not found"));
        return modelMapper.map(device, DeviceDTO.class);
    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(device -> modelMapper.map(device, DeviceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDTO createDevice(AddDeviceDTO deviceDTO) {
        Device device = modelMapper.map(deviceDTO, Device.class);
        device.setCreated(new Date());
        return modelMapper.map(deviceRepository.save(device), DeviceDTO.class);
    }

    @Override
    public DeviceDTO updateDevice(UUID id, DeviceDTO deviceDTO) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Device not found"));
        modelMapper.map(deviceDTO, device);
        device.setModified(new Date());
        return modelMapper.map(deviceRepository.save(device), DeviceDTO.class);
    }

    @Override
    public void deleteDevice(UUID id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public DeviceDTO getDeviceByName(String name) {
        Device device = deviceRepository.findByName(name);
        if (device == null) {
            throw new RuntimeException("Device not found");
        }
        return modelMapper.map(device, DeviceDTO.class);
    }


}