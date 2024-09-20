package com.example.iotservice.services.impl;

import com.example.iotservice.dtos.AddHubDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.models.Hub;
import com.example.iotservice.models.Device;
import com.example.iotservice.repositories.HubRepository;
import com.example.iotservice.repositories.DeviceRepository;
import com.example.iotservice.services.HubService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HubServiceImpl implements HubService {

    private HubRepository controllerRepository;
    private DeviceRepository deviceRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setControllerRepository(HubRepository controllerRepository) {
        this.controllerRepository = controllerRepository;
    }

    @Autowired
    public void setDeviceRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public HubDTO getHubById(UUID id) {
        Hub controller = controllerRepository.findById(id).orElseThrow(() -> new RuntimeException("Controller not found"));
        return modelMapper.map(controller, HubDTO.class);
    }

    @Override
    public List<HubDTO> getAllHubs() {
        return controllerRepository.findAll().stream()
                .map(controller -> modelMapper.map(controller, HubDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HubDTO createHub(AddHubDTO controllerDTO) {
        Hub controller = modelMapper.map(controllerDTO, Hub.class);
        controller.setModified(new Date());
        return modelMapper.map(controllerRepository.save(controller), HubDTO.class);
    }

    @Override
    public HubDTO updateHub(UUID id, HubDTO controllerDTO) {
        Hub controller = controllerRepository.findById(id).orElseThrow(() -> new RuntimeException("Controller not found"));
        modelMapper.map(controllerDTO, controller);
        controller.setModified(new Date());
        return modelMapper.map(controllerRepository.save(controller), HubDTO.class);
    }

    @Override
    public void deleteHub(UUID id) {
        controllerRepository.deleteById(id);
    }

    @Override
    public List<DeviceDTO> getDevicesByHubId(UUID controllerId) {
        List<Device> devices = deviceRepository.findByControllerId(controllerId);
        return devices.stream()
                .map(device -> modelMapper.map(device, DeviceDTO.class))
                .collect(Collectors.toList());
    }
}