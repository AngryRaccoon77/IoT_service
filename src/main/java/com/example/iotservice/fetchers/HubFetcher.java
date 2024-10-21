package com.example.iotservice.fetchers;

import com.example.iotservice.dtos.AddHubDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.dtos.HubDTO;
import com.example.iotservice.models.Hub;
import com.example.iotservice.models.enums.ControllerType;
import com.example.iotservice.services.HouseService;
import com.example.iotservice.services.HubService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class HubFetcher {

    private HubService hubService;

    @Autowired
    public void setHouseService(HubService hubService) {
        this.hubService = hubService;
    }

    @DgsQuery
    public List<HubDTO> hubs() {
        return hubService.getAllHubs();
    }

    @DgsQuery
    public Optional<HubDTO> hub(UUID id) {
        return Optional.of(hubService.getHubById(id));
    }

    @DgsMutation
    public HubDTO addHub(String name, ControllerType type, Boolean status, HouseDTO house) {
        AddHubDTO addHubDTO = new AddHubDTO();
        addHubDTO.setName(name);
        addHubDTO.setType(type);
        addHubDTO.setStatus(status);
        addHubDTO.setHouse(house);
        return hubService.createHub(addHubDTO);
    }

    @DgsMutation
    public Boolean deleteHub(UUID id) {
        try {
            hubService.deleteHub(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}