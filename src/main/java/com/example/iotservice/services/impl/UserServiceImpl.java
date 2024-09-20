package com.example.iotservice.services.impl;

import com.example.iotservice.dtos.AddUserDTO;
import com.example.iotservice.dtos.UserDTO;
import com.example.iotservice.models.User;
import com.example.iotservice.repositories.UserRepository;
import com.example.iotservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    private ModelMapper modelMapper;

    @Override
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(AddUserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setCreated(new Date());
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        modelMapper.map(userDTO, user);
        user.setModified(new  Date());
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}