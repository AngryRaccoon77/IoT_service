package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddDeviceDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.dtos.DeviceServiceDTO;
import com.example.iotservice.dtos.UpdateDeviceDTO;
import com.example.iotservice.services.DeviceService;
import com.example.iotservice.services.DeviceServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceServiceService deviceServiceService;

    @Autowired
    public DeviceController(DeviceService deviceService, DeviceServiceService deviceServiceService) {
        this.deviceService = deviceService;
        this.deviceServiceService = deviceServiceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Map<String, Object>>> getDeviceById(@PathVariable UUID id) {
        DeviceDTO deviceDTO = deviceService.getDeviceById(id);
        Map<String, Object> deviceData = new HashMap<>();
        deviceData.put("id", deviceDTO.getId());
        deviceData.put("name", deviceDTO.getName());

        Map<String, Object> linkMap = new HashMap<>();
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(id)).withSelfRel();
        linkMap.put("self", selfLink);

        Link hubLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(deviceDTO.getHub().getId())).withRel("hub");
        linkMap.put("hub", hubLink);

        List<Link> serviceLinks = new ArrayList<>();
        List<DeviceServiceDTO> services = deviceServiceService.getDeviceServicesByDeviceId(id);
        for (DeviceServiceDTO service : services) {
            Link serviceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).getDeviceServiceById(service.getId())).withRel("service");
            serviceLinks.add(serviceLink);
        }

        linkMap.put("services", serviceLinks);
        deviceData.put("_links", linkMap);

        Map<String, Map<String, Object>> actionMap = new HashMap<>();
        Map<String, Object> addServiceAction = new HashMap<>();
        addServiceAction.put("href", WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).createDeviceService(null)).withRel("addService").getHref());
        addServiceAction.put("method", "POST");
        actionMap.put("addService", addServiceAction);

        Map<String, Object> updateAction = new HashMap<>();
        updateAction.put("href", WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).updateDevice(id, null)).withRel("updateDevice").getHref());
        updateAction.put("method", "PUT");
        actionMap.put("update", updateAction);

        Map<String, Object> deleteAction = new HashMap<>();
        deleteAction.put("href", WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).deleteDevice(id)).withRel("deleteDevice").getHref());
        deleteAction.put("method", "DELETE");
        actionMap.put("delete", deleteAction);

        deviceData.put("_action", actionMap);

        EntityModel<Map<String, Object>> resource = EntityModel.of(deviceData);

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DeviceDTO>>> getAllDevices() {
        List<EntityModel<DeviceDTO>> devices = deviceService.getAllDevices().stream()
                .map(deviceDTO -> {
                    EntityModel<DeviceDTO> resource = EntityModel.of(deviceDTO);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(deviceDTO.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());

        Link addDeviceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).createDevice(null)).withRel("addDevice");

        Map<String, Object> addDeviceAction = new HashMap<>();
        addDeviceAction.put("href", addDeviceLink.getHref());
        addDeviceAction.put("method", "POST");

        Map<String, Map<String, Object>> actionMap = new HashMap<>();
        actionMap.put("addDevice", addDeviceAction);

        CollectionModel<EntityModel<DeviceDTO>> collectionModel = CollectionModel.of(devices);
        collectionModel.add(Link.of(addDeviceLink.getHref(), "_action").withType("POST"));

        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<DeviceDTO>> createDevice(@RequestBody AddDeviceDTO deviceDTO) {
        DeviceDTO createdDevice = deviceService.createDevice(deviceDTO);
        EntityModel<DeviceDTO> resource = EntityModel.of(createdDevice);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(createdDevice.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DeviceDTO>> updateDevice(@PathVariable UUID id, @RequestBody UpdateDeviceDTO deviceDTO) {
        DeviceDTO updatedDevice = deviceService.updateDevice(id, deviceDTO);
        EntityModel<DeviceDTO> resource = EntityModel.of(updatedDevice);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(id)).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}