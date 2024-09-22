package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddHouseDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.services.HouseService;
import com.example.iotservice.services.HubService;
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
@RequestMapping("/houses")
public class HouseController {

    private final HouseService houseService;
    private final HubService hubService;


    @Autowired
    public HouseController(HouseService houseService, HubService hubService) {
        this.houseService = houseService;
        this.hubService = hubService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<HouseDTO>> getHouseById(@PathVariable UUID id) {
        HouseDTO houseDTO = houseService.getHouseById(id);
        EntityModel<HouseDTO> resource = EntityModel.of(houseDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(id)).withSelfRel();
        resource.add(selfLink);

        Link userLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(houseDTO.getUser().getId())).withRel("user");
        resource.add(userLink);

        List<HubDTO> hubs = hubService.getHubsByHouseId(id);
        for(HubDTO hub : hubs) {
            Link hubLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(hub.getId())).withRel("hub");
            resource.add(hubLink);
        }
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<HouseDTO>>> getAllHouses() {
        List<EntityModel<HouseDTO>> houses = houseService.getAllHouses().stream()
                .map(houseDTO -> {
                    EntityModel<HouseDTO> resource = EntityModel.of(houseDTO);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(houseDTO.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(houses);
    }

    @PostMapping
    public ResponseEntity<EntityModel<HouseDTO>> createHouse(@RequestBody AddHouseDTO houseDTO) {
        HouseDTO createdHouse = houseService.createHouse(houseDTO);
        EntityModel<HouseDTO> resource = EntityModel.of(createdHouse);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(createdHouse.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HouseDTO>> updateHouse(@PathVariable UUID id, @RequestBody HouseDTO houseDTO) {
        HouseDTO updatedHouse = houseService.updateHouse(id, houseDTO);
        EntityModel<HouseDTO> resource = EntityModel.of(updatedHouse);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(id)).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable UUID id) {
        houseService.deleteHouse(id);
        return ResponseEntity.noContent().build();
    }
}