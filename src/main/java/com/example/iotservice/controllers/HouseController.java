package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddHouseDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.services.HouseService;
import com.example.iotservice.services.HubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    public ResponseEntity<EntityModel<Map<String, Object>>> getHouseById(@PathVariable UUID id) {
        HouseDTO houseDTO = houseService.getHouseById(id);
        Map<String, Object> houseData = new HashMap<>();
        houseData.put("id", houseDTO.getId());
        houseData.put("name", houseDTO.getName());
        houseData.put("address", houseDTO.getAddress());

        Map<String, Object> linkMap = new HashMap<>();
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(id)).withSelfRel();
        linkMap.put("self", selfLink);

        Link userLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(houseDTO.getUser().getId())).withRel("user");
        linkMap.put("user", userLink);
        houseData.put("_links", linkMap);

        Map<String, Link> actionMap = new HashMap<>();
        Link addHubLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).createHub(null)).withRel("addHub");
        actionMap.put("addHub", addHubLink);

        List<HubDTO> hubs = hubService.getHubsByHouseId(id);
        List<Link> hubLinks = new ArrayList<>();
        for(HubDTO hub : hubs) {
            Link hubLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(hub.getId())).withRel("hub");
            hubLinks.add(hubLink);
        }
        linkMap.put("hub", hubLinks);
        Link updateHouseLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).updateHouse(houseDTO.getId(), null)).withRel("updateHouse");
        actionMap.put("updateHouse", updateHouseLink);

        Link deleteLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).deleteHouse(id)).withRel("deleteHouse");
        actionMap.put("delete", deleteLink);
        houseData.put("_action", actionMap);

        EntityModel<Map<String, Object>> resource = EntityModel.of(houseData);

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HouseDTO>>> getAllHouses() {
        List<EntityModel<HouseDTO>> houses = houseService.getAllHouses().stream()
                .map(houseDTO -> {
                    EntityModel<HouseDTO> resource = EntityModel.of(houseDTO);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(houseDTO.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());

        Link addHouseLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).createHouse(null)).withRel("addHouse");

        Map<String, Object> addHouseAction = new HashMap<>();
        addHouseAction.put("href", addHouseLink.getHref());
        addHouseAction.put("method", "POST");

        Map<String, Map<String, Object>> actionMap = new HashMap<>();
        actionMap.put("addHouse", addHouseAction);

        CollectionModel<EntityModel<HouseDTO>> collectionModel = CollectionModel.of(houses);
        collectionModel.add(Link.of(addHouseLink.getHref(), "_action").withType("POST"));

        return ResponseEntity.ok(collectionModel);
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