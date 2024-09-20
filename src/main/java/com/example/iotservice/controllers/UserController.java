package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddUserDTO;
import com.example.iotservice.dtos.UserDTO;
import com.example.iotservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getUserById(@PathVariable UUID id) {
        UserDTO userDTO = userService.getUserById(id);
        EntityModel<UserDTO> resource = EntityModel.of(userDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<UserDTO>>> getAllUsers() {
        List<EntityModel<UserDTO>> users = userService.getAllUsers().stream()
                .map(userDTO -> {
                    EntityModel<UserDTO> resource = EntityModel.of(userDTO);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(userDTO.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserDTO>> createUser(@RequestBody AddUserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        EntityModel<UserDTO> resource = EntityModel.of(createdUser);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        EntityModel<UserDTO> resource = EntityModel.of(updatedUser);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}