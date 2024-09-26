package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddHubDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.services.HubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
@RequestMapping("/hubs")
public class HubController {

    private final HubService hubService;

    @Autowired
    public HubController(HubService hubService) {
        this.hubService = hubService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<HubDTO>> getHubById(@PathVariable UUID id) {
        HubDTO hubDTO = hubService.getHubById(id);
        EntityModel<HubDTO> resource = EntityModel.of(hubDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(id)).withSelfRel();
        resource.add(selfLink);

        Link houseLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(hubDTO.getHouse().getId())).withRel("house");
        resource.add(houseLink);

        Link addDeviceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).createDevice(null)).withRel("addDevice");
        resource.add(addDeviceLink);

        List<DeviceDTO> devices = hubService.getDevicesByHubId(id);
        for(DeviceDTO device : devices) {
            Link deviceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(device.getId())).withRel("device");
            resource.add(deviceLink);
        }

        Link updateLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).updateHub(id, hubDTO)).withRel("update");
        resource.add(updateLink);

        Link deleteLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).deleteHub(id)).withRel("delete");
        resource.add(deleteLink);

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HubDTO>>> getAllHubs() {
        List<EntityModel<HubDTO>> hubs = hubService.getAllHubs().stream()
                .map(hubDTO -> {
                    EntityModel<HubDTO> resource = EntityModel.of(hubDTO);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(hubDTO.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());

        Link addHubLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).createHub(null)).withRel("addHub");
        CollectionModel<EntityModel<HubDTO>> collectionModel = CollectionModel.of(hubs, addHubLink);
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<HubDTO>> createHub(@RequestBody AddHubDTO hubDTO) {
        HubDTO createdHub = hubService.createHub(hubDTO);
        EntityModel<HubDTO> resource = EntityModel.of(createdHub);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(createdHub.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HubDTO>> updateHub(@PathVariable UUID id, @RequestBody HubDTO hubDTO) {
        HubDTO updatedHub = hubService.updateHub(id, hubDTO);
        EntityModel<HubDTO> resource = EntityModel.of(updatedHub);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(id)).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHub(@PathVariable UUID id) {
        hubService.deleteHub(id);
        return ResponseEntity.noContent().build();
    }
}