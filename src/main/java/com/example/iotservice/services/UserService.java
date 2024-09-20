package com.example.iotservice.services;

import com.example.iotservice.dtos.AddUserDTO;
import com.example.iotservice.dtos.UserDTO;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID id);
    List<UserDTO> getAllUsers();
    UserDTO createUser(AddUserDTO userDTO);
    UserDTO updateUser(UUID id, UserDTO userDTO);
    void deleteUser(UUID id);
}