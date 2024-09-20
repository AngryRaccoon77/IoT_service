package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddDeviceDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.services.DeviceService;
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
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DeviceDTO>> getDeviceById(@PathVariable UUID id) {
        DeviceDTO deviceDTO = deviceService.getDeviceById(id);
        EntityModel<DeviceDTO> resource = EntityModel.of(deviceDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(id)).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<DeviceDTO>>> getAllDevices() {
        List<EntityModel<DeviceDTO>> devices = deviceService.getAllDevices().stream()
                .map(deviceDTO -> {
                    EntityModel<DeviceDTO> resource = EntityModel.of(deviceDTO);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(deviceDTO.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(devices);
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
    public ResponseEntity<EntityModel<DeviceDTO>> updateDevice(@PathVariable UUID id, @RequestBody DeviceDTO deviceDTO) {
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