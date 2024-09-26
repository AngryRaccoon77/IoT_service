package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddDeviceServiceDTO;
import com.example.iotservice.dtos.DeviceServiceDTO;
import com.example.iotservice.services.DeviceServiceService;
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
@RequestMapping("/device-services")
public class DeviceServiceController {

    private final DeviceServiceService deviceServiceService;

    @Autowired
    public DeviceServiceController(DeviceServiceService deviceServiceService) {
        this.deviceServiceService = deviceServiceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DeviceServiceDTO>> getDeviceServiceById(@PathVariable UUID id) {
        DeviceServiceDTO deviceServiceDTO = deviceServiceService.getDeviceServiceById(id);
        EntityModel<DeviceServiceDTO> resource = EntityModel.of(deviceServiceDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).getDeviceServiceById(id)).withSelfRel();
        resource.add(selfLink);

        Link deleteLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).deleteDeviceService(id)).withRel("delete");
        resource.add(deleteLink);

        Link updateLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).updateDeviceService(id, deviceServiceDTO)).withRel("update");
        resource.add(updateLink);

        Link deviceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(deviceServiceDTO.getDevice().getId())).withRel("device");
        resource.add(deviceLink);
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DeviceServiceDTO>>> getAllDeviceServices() {
        List<EntityModel<DeviceServiceDTO>> deviceServices = deviceServiceService.getAllDeviceServices().stream()
                .map(deviceServiceDTO -> {
                    EntityModel<DeviceServiceDTO> resource = EntityModel.of(deviceServiceDTO);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).getDeviceServiceById(deviceServiceDTO.getId())).withSelfRel();
                    resource.add(selfLink);
                    return resource;
                })
                .collect(Collectors.toList());

        Link addDeviceServiceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).createDeviceService(null)).withRel("addDeviceService");
        CollectionModel<EntityModel<DeviceServiceDTO>> collectionModel = CollectionModel.of(deviceServices, addDeviceServiceLink);
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<DeviceServiceDTO>> createDeviceService(@RequestBody AddDeviceServiceDTO deviceServiceDTO) {
        DeviceServiceDTO createdDeviceService = deviceServiceService.createDeviceService(deviceServiceDTO);
        EntityModel<DeviceServiceDTO> resource = EntityModel.of(createdDeviceService);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).getDeviceServiceById(createdDeviceService.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DeviceServiceDTO>> updateDeviceService(@PathVariable UUID id, @RequestBody DeviceServiceDTO deviceServiceDTO) {
        DeviceServiceDTO updatedDeviceService = deviceServiceService.updateDeviceService(id, deviceServiceDTO);
        EntityModel<DeviceServiceDTO> resource = EntityModel.of(updatedDeviceService);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).getDeviceServiceById(id)).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceService(@PathVariable UUID id) {
        deviceServiceService.deleteDeviceService(id);
        return ResponseEntity.noContent().build();
    }
}