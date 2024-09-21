package com.example.iotservice.services.impl;

import com.example.iotservice.dtos.AddHouseDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.models.House;
import com.example.iotservice.models.User;
import com.example.iotservice.repositories.HouseRepository;
import com.example.iotservice.services.HouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl implements HouseService {

    private HouseRepository houseRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setHouseRepository(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public HouseDTO getHouseById(UUID id) {
        House house = houseRepository.findById(id).orElseThrow(() -> new RuntimeException("House not found"));
        return modelMapper.map(house, HouseDTO.class);
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        return houseRepository.findAll().stream()
                .map(house -> modelMapper.map(house, HouseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HouseDTO createHouse(AddHouseDTO houseDTO) {
        House house = modelMapper.map(houseDTO, House.class);
        house.setCreated(new Date());
        return modelMapper.map(houseRepository.save(house), HouseDTO.class);
    }

    @Override
    public HouseDTO updateHouse(UUID id, HouseDTO houseDTO) {
        House house = houseRepository.findById(id).orElseThrow(() -> new RuntimeException("House not found"));
        modelMapper.map(houseDTO, house);
        house.setModified(new Date());
        return modelMapper.map(houseRepository.save(house), HouseDTO.class);
    }

    @Override
    public void deleteHouse(UUID id) {
        houseRepository.deleteById(id);
    }

    @Override
    public List<HouseDTO> getHousesByUserId(UUID userId) {
        return houseRepository.findByUserId(userId)  // Вызываем репозиторий для получения домов
                .stream()
                .map(house -> modelMapper.map(house, HouseDTO.class))  // Маппим сущности в DTO
                .collect(Collectors.toList());
    }


}