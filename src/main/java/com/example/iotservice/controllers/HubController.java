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

import java.util.*;
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
    public ResponseEntity<EntityModel<Map<String, Object>>> getHubById(@PathVariable UUID id) {
        HubDTO hubDTO = hubService.getHubById(id);
        Map<String, Object> hubData = new HashMap<>();
        hubData.put("id", hubDTO.getId());
        hubData.put("name", hubDTO.getName());
        hubData.put("type", hubDTO.getType());

        Map<String, Object> linkMap = new HashMap<>();
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(id)).withSelfRel();
        linkMap.put("self", selfLink);

        Link houseLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getHouseById(hubDTO.getHouse().getId())).withRel("house");
        linkMap.put("house", houseLink);
        hubData.put("_links", linkMap);

        Map<String, Map<String, Object>> actionMap = new HashMap<>();
        Map<String, Object> addDeviceAction = new HashMap<>();
        Link addDeviceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).createDevice(null)).withRel("addDevice");
        addDeviceAction.put("href", addDeviceLink.getHref());
        addDeviceAction.put("method", "POST");
        actionMap.put("addDevice", addDeviceAction);

        List<DeviceDTO> devices = hubService.getDevicesByHubId(id);
        List<Link> deviceLinks = new ArrayList<>();
        for (DeviceDTO device : devices) {
            Link deviceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(device.getId())).withRel("device");
            deviceLinks.add(deviceLink);
        }
        linkMap.put("devices", deviceLinks);

        Map<String, Object> updateAction = new HashMap<>();
        Link updateLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).updateHub(id, hubDTO)).withRel("updateHub");
        updateAction.put("href", updateLink.getHref());
        updateAction.put("method", "PUT");
        actionMap.put("update", updateAction);

        Map<String, Object> deleteAction = new HashMap<>();
        Link deleteLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).deleteHub(id)).withRel("deleteHub");
        deleteAction.put("href", deleteLink.getHref());
        deleteAction.put("method", "DELETE");
        actionMap.put("delete", deleteAction);

        hubData.put("_action", actionMap);

        EntityModel<Map<String, Object>> resource = EntityModel.of(hubData);

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

        Map<String, Object> addHubAction = new HashMap<>();
        addHubAction.put("href", addHubLink.getHref());
        addHubAction.put("method", "POST");

        Map<String, Map<String, Object>> actionMap = new HashMap<>();
        actionMap.put("addHub", addHubAction);

        CollectionModel<EntityModel<HubDTO>> collectionModel = CollectionModel.of(hubs);
        collectionModel.add(Link.of(addHubLink.getHref(), "_action").withType("POST"));

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

    @PutMapping(value = "/{id}", consumes = "application/json")
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