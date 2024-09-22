package com.example.iotservice.services.impl;

import com.example.iotservice.dtos.AddDeviceServiceDTO;
import com.example.iotservice.dtos.DeviceServiceDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.models.DeviceService;
import com.example.iotservice.repositories.DeviceServiceRepository;
import com.example.iotservice.services.DeviceServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceServiceServiceImpl implements DeviceServiceService {

    private DeviceServiceRepository serviceRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setServiceRepository(DeviceServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DeviceServiceDTO getDeviceServiceById(UUID id) {
        DeviceService service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        return modelMapper.map(service, DeviceServiceDTO.class);
    }

    @Override
    public List<DeviceServiceDTO> getAllDeviceServices() {
        return serviceRepository.findAll().stream()
                .map(service -> modelMapper.map(service, DeviceServiceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DeviceServiceDTO createDeviceService(AddDeviceServiceDTO serviceDTO) {
        DeviceService service = modelMapper.map(serviceDTO, DeviceService.class);
        service.setCreated(new Date());
        return modelMapper.map(serviceRepository.save(service), DeviceServiceDTO.class);
    }

    @Override
    public DeviceServiceDTO updateDeviceService(UUID id, DeviceServiceDTO serviceDTO) {
        DeviceService service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        modelMapper.map(serviceDTO, service);
        service.setModified(new Date());
        return modelMapper.map(serviceRepository.save(service), DeviceServiceDTO.class);
    }

    @Override
    public void deleteDeviceService(UUID id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public List<DeviceServiceDTO> getDeviceServicesByDeviceId(UUID deviceId) {
        return serviceRepository.findByDeviceId(deviceId)
                .stream()
                .map(device -> modelMapper.map(device, DeviceServiceDTO.class))
                .collect(Collectors.toList());
    }
}