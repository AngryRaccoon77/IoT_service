package com.example.iotservice.services.impl;

import com.example.iotservice.dtos.AddHubDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.models.Hub;
import com.example.iotservice.models.Device;
import com.example.iotservice.repositories.HubRepository;
import com.example.iotservice.repositories.DeviceRepository;
import com.example.iotservice.services.HubService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HubServiceImpl implements HubService {

    private HubRepository hubRepository;
    private DeviceRepository deviceRepository;
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(HubServiceImpl.class);


    @Autowired
    public void setControllerRepository(HubRepository hubRepository) {
        this.hubRepository = hubRepository;
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
        Hub controller = hubRepository.findById(id).orElseThrow(() -> new RuntimeException("Controller not found"));
        return modelMapper.map(controller, HubDTO.class);
    }

    @Override
    public List<HubDTO> getAllHubs() {
        return hubRepository.findAll().stream()
                .map(controller -> modelMapper.map(controller, HubDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HubDTO createHub(AddHubDTO hubDTO) {
        Hub hub = modelMapper.map(hubDTO, Hub.class);
        hub.setCreated(new Date());
        return modelMapper.map(hubRepository.save(hub), HubDTO.class);
    }

    @Override
    public HubDTO updateHub(UUID id, HubDTO hubDTO) {
        Hub hub = hubRepository.findById(id).orElseThrow(() -> new RuntimeException("Hub not found"));

        logger.info("Hub before update: {}", hub);

        // Маппинг DTO на сущность
        modelMapper.map(hubDTO, hub);

        logger.info("Hub after update: {}", hub);

        hub.setModified(new Date());
        return modelMapper.map(hubRepository.save(hub), HubDTO.class);
    }


    @Override
    public void deleteHub(UUID id) {
        hubRepository.deleteById(id);
    }

    @Override
    public List<DeviceDTO> getDevicesByHubId(UUID hubId) {
        List<Device> devices = deviceRepository.findByHubId(hubId);
        return devices.stream()
                .map(device -> modelMapper.map(device, DeviceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<HubDTO> getHubsByHouseId(UUID houseId) {
        return hubRepository.findByHouseId(houseId)
                .stream()
                .map(house -> modelMapper.map(house, HubDTO.class))
                .collect(Collectors.toList());
    }
}