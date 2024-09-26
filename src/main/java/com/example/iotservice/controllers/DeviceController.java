package com.example.iotservice.controllers;

import com.example.iotservice.dtos.AddDeviceDTO;
import com.example.iotservice.dtos.DeviceDTO;
import com.example.iotservice.dtos.DeviceServiceDTO;
import com.example.iotservice.services.DeviceService;
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
    public ResponseEntity<EntityModel<DeviceDTO>> getDeviceById(@PathVariable UUID id) {
        DeviceDTO deviceDTO = deviceService.getDeviceById(id);
        EntityModel<DeviceDTO> resource = EntityModel.of(deviceDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).getDeviceById(id)).withSelfRel();
        resource.add(selfLink);

        Link hubLink = WebMvcLinkBuilder.linkTo(methodOn(HubController.class).getHubById(deviceDTO.getHub().getId())).withRel("hub");
        resource.add(hubLink);

        Link deleteLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).deleteDevice(id)).withRel("delete");
        resource.add(deleteLink);

        Link updateLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceController.class).updateDevice(id, deviceDTO)).withRel("update");
        resource.add(updateLink);

        Link addDeviceServiceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).createDeviceService(null)).withRel("addDeviceService");
        resource.add(addDeviceServiceLink);

        List<DeviceServiceDTO> services = deviceServiceService.getDeviceServicesByDeviceId(id);
        for(DeviceServiceDTO service : services) {
            Link serviceLink = WebMvcLinkBuilder.linkTo(methodOn(DeviceServiceController.class).getDeviceServiceById(service.getId())).withRel("service");
            resource.add(serviceLink);
        }


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
        CollectionModel<EntityModel<DeviceDTO>> collectionModel = CollectionModel.of(devices, addDeviceLink);
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