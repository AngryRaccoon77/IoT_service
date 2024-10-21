package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddUserDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.dtos.UserDTO;
import com.example.iotservice.services.HouseService;
import com.example.iotservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final HouseService houseService;

    @Autowired
    public UserController(UserService userService, HouseService houseService) {
        this.userService = userService;
        this.houseService = houseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getUserById(@PathVariable UUID id) {
        UserDTO userDTO = userService.getUserById(id);
        EntityModel<UserDTO> resource = EntityModel.of(userDTO);


        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
        resource.add(selfLink);

        List<HouseDTO> houses = houseService.getHousesByUserId(id);
        for (HouseDTO house : houses) {
            Link houseLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(house.getId())).withRel("house");
            resource.add(houseLink);
        }

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> getAllUsers() {
        List<EntityModel<UserDTO>> users = userService.getAllUsers().stream()
                .map(userDTO -> {
                    EntityModel<UserDTO> resource = EntityModel.of(userDTO);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(userDTO.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());

        Link addUserLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).createUser(null)).withRel("addUser");

        Map<String, Object> addUserAction = new HashMap<>();
        addUserAction.put("href", addUserLink.getHref());
        addUserAction.put("method", "POST");

        Map<String, Map<String, Object>> actionMap = new HashMap<>();
        actionMap.put("addUser", addUserAction);

        CollectionModel<EntityModel<UserDTO>> collectionModel = CollectionModel.of(users);
        collectionModel.add(Link.of(addUserLink.getHref(), "_action").withType("POST"));

        return ResponseEntity.ok(collectionModel);
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