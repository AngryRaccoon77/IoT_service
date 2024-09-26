package com.example.iotservice.controllers;

import com.example.iotservice.models.Message;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping
    public ResponseEntity<EntityModel<Message>> getRoot() {
        Message message = new Message("Welcome to the IoT Service API");
        EntityModel<Message> resource = EntityModel.of(message);

        Link housesLink = WebMvcLinkBuilder.linkTo(methodOn(HouseController.class).getAllHouses()).withRel("houses");
        resource.add(housesLink);

        return ResponseEntity.ok(resource);
    }
}