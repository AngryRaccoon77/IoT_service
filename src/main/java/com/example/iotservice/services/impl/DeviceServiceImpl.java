package com.example.iotservice.services.impl;

import com.example.iotservice.dtos.AddDeviceDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.dtos.DeviceServiceDTO;
import com.example.iotservice.dtos.UpdateDeviceDTO;
import com.example.iotservice.models.Device;
import com.example.iotservice.repositories.DeviceRepository;
import com.example.iotservice.services.DeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private RabbitTemplate rabbitTemplate;


    @Autowired
    public void setDeviceRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
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
    public DeviceDTO updateDevice(UUID id, UpdateDeviceDTO deviceDTO) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        boolean oldStatus = device.getStatus();
        device.setName(deviceDTO.getName());
        device.setStatus(deviceDTO.getStatus());
        device.setModified(new Date());
        Device savedDevice = deviceRepository.save(device);
        DeviceDTO updatedDevice = modelMapper.map(savedDevice, DeviceDTO.class);
        rabbitTemplate.convertAndSend("deviceStatusExchange", "device.status", updatedDevice.toString());

        return updatedDevice;
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