package com.example.iotservice.fetchers;

import com.example.iotservice.dtos.AddHouseDTO;
import com.example.iotservice.dtos.HouseDTO;
import com.example.iotservice.dtos.UserDTO;
import com.example.iotservice.models.User;
import com.example.iotservice.services.HouseService;
import com.example.iotservice.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class HouseFetcher {

    private HouseService houseService;
    private UserService userService;

    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @DgsQuery
    public List<HouseDTO> houses() {
        return houseService.getAllHouses();
    }

    @DgsQuery
    public Optional<HouseDTO> house(UUID id) {
        return Optional.of(houseService.getHouseById(id));
    }
    @DgsMutation
    public HouseDTO addHouse(String address, UUID userID) {
        UserDTO user = userService.getUserById(userID);
        AddHouseDTO addHouseDTO = new AddHouseDTO();
        addHouseDTO.setAddress(address);
        addHouseDTO.setUser(user);
        return houseService.createHouse(addHouseDTO);
    }

    @DgsMutation
    public Boolean deleteHouse(UUID id) {
        try {
            houseService.deleteHouse(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}